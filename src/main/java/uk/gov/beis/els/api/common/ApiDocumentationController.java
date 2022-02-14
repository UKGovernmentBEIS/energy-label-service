package uk.gov.beis.els.api.common;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.beis.els.api.model.TagLink;
import uk.gov.beis.els.mvc.ReverseRouter;

@Controller
public class ApiDocumentationController {

  @GetMapping(path = "/api-documentation")
  public ModelAndView getApiDocumentation(HttpServletRequest request) {
    OpenAPI openAPIResponse = getOpenApiResponse(request);

    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationStartPage");
    List<TagLink> tagLinks = openAPIResponse.getTags().stream()
        .map(this::getTagLink)
        .sorted(Comparator.comparing(TagLink::getName))
        .collect(Collectors.toList());
    modelAndView.addObject("tagLinks", tagLinks);
    return modelAndView;
  }

  @GetMapping("/api-documentation/api")
  public ModelAndView getApiDocumentationForTag(@RequestParam("tag") String tag, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationForTag");
    modelAndView.addObject("tagName", tag);
    OpenAPI openAPI = getOpenApiResponse(request);
    List<Operation> operationList = openAPI.getPaths().values().stream()
        .map(PathItem::getPost)
        .filter(post -> post.getTags().contains(tag))
        .sorted(Comparator.comparing(Operation::getSummary))
        .collect(Collectors.toList());
    modelAndView.addObject("operationList", operationList);
    return modelAndView;
  }

  private OpenAPI getOpenApiResponse(HttpServletRequest request) {
    String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
        .replacePath(null)
        .build()
        .toUriString();
    RestTemplate restTemplate = new RestTemplate();
   return restTemplate.getForEntity(baseUrl + "/v3/api-docs/", OpenAPI.class).getBody();
  }

  private TagLink getTagLink(Tag tag) {
    return new TagLink(
        tag.getName(),
        ReverseRouter.route(on(ApiDocumentationController.class).getApiDocumentationForTag(tag.getName(), null))
    );
  }
}
