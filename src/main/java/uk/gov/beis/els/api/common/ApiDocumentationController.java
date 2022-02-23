package uk.gov.beis.els.api.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.api.openapi.OpenApiService;

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
    getCommonModelAndViewAttributes(modelAndView, request);
    modelAndView.addObject("serviceHomePageUrl", serviceHomePageUrl);
    return modelAndView;
  }

  @GetMapping("/api-documentation/endpoint/{tag}")
  public ModelAndView getApiDocumentationForTag(@PathVariable("tag") String tag, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationForTag");
    getCommonModelAndViewAttributes(modelAndView, request);
    modelAndView.addObject("tagName", tag);
    modelAndView.addObject("operationListWithPath", openApiService.getOperationWithPathForTag(tag));
    modelAndView.addObject("baseUrl", openApiService.getBaseUrl(request));
    return modelAndView;
  }

  private void getCommonModelAndViewAttributes(ModelAndView modelAndView, HttpServletRequest request) {
    modelAndView.addObject("tagLinks", openApiService.getTagLinks());
    modelAndView.addObject("currentUrl", request.getRequestURI());
    modelAndView.addObject("apiSpecUrl", openApiService.getApiSpecUrl(request));
  }


}
