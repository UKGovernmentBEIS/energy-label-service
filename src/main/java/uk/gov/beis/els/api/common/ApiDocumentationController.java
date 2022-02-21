package uk.gov.beis.els.api.common;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.api.model.TagLink;
import uk.gov.beis.els.api.openapi.OpenApiService;
import uk.gov.beis.els.mvc.ReverseRouter;

@Controller
public class ApiDocumentationController {

  private final OpenApiService openApiService;

  @Autowired
  public ApiDocumentationController(OpenApiService openApiService) {
    this.openApiService = openApiService;
  }

  @GetMapping(path = "/api-documentation")
  public ModelAndView getApiDocumentation(@Value("${app.home_page_url}") String serviceHomePageUrl, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationStartPage");
    List<TagLink> tagLinks = openApiService.getOpenAPISpec().getTags().stream()
        .map(this::getTagLink)
        .sorted(Comparator.comparing(TagLink::getName))
        .collect(Collectors.toList());
    modelAndView.addObject("tagLinks", tagLinks);
    modelAndView.addObject("currentUrl", request.getRequestURI());
    modelAndView.addObject("serviceHomePageUrl", serviceHomePageUrl);
    return modelAndView;
  }

  @GetMapping("/api-documentation/endpoint/{tag}")
  public ModelAndView getApiDocumentationForTag(@PathVariable("tag") String tag, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationForTag");
    modelAndView.addObject("tagName", tag);
    List<Operation> operationList = openApiService.getOpenAPISpec().getPaths().values().stream()
        .map(PathItem::getPost)
        .filter(post -> post.getTags().contains(tag))
        .sorted(Comparator.comparing(Operation::getSummary))
        .collect(Collectors.toList());
    modelAndView.addObject("operationList", operationList);
    List<TagLink> tagLinks = openApiService.getOpenAPISpec().getTags().stream()
        .map(this::getTagLink)
        .sorted(Comparator.comparing(TagLink::getName))
        .collect(Collectors.toList());
    modelAndView.addObject("tagLinks", tagLinks);
    modelAndView.addObject("currentUrl", request.getRequestURI());
    return modelAndView;
  }

  private TagLink getTagLink(Tag tag) {
    return new TagLink(
        tag.getName(),
        ReverseRouter.route(on(ApiDocumentationController.class).getApiDocumentationForTag(tag.getName(), null))
    );
  }
}
