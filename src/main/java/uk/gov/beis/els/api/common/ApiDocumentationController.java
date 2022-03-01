package uk.gov.beis.els.api.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.api.openapi.OpenApiService;
import uk.gov.beis.els.service.Bucket4JRequestService;

@Controller
public class ApiDocumentationController {

  private final OpenApiService openApiService;
  private final Bucket4JRequestService bucket4JRequestService;

  @Autowired
  public ApiDocumentationController(OpenApiService openApiService,
                                    Bucket4JRequestService bucket4JRequestService) {
    this.openApiService = openApiService;
    this.bucket4JRequestService = bucket4JRequestService;
  }

  @GetMapping(path = "/api-documentation")
  public ModelAndView getApiDocumentation(@Value("${app.home_page_url}") String serviceHomePageUrl, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationStartPage");
    getCommonModelAndViewAttributes(modelAndView, request);
    modelAndView
        .addObject("serviceHomePageUrl", serviceHomePageUrl)
        .addObject("rateLimitCapacity", bucket4JRequestService.getRateLimitCapacity())
        .addObject("rateLimitTimeValue", bucket4JRequestService.getRateLimitTimeValue())
        .addObject("rateLimitTimeUnit", bucket4JRequestService.getRateLimitTimeUnit())
        .addObject("apiVersion", openApiService.getAPIVersion());
    return modelAndView;
  }

  @GetMapping("/api-documentation/endpoint/{tag}")
  public ModelAndView getApiDocumentationForTag(@PathVariable("tag") String tag, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView("/apidocumentation/apiDocumentationForTag");
    getCommonModelAndViewAttributes(modelAndView, request);
    modelAndView
        .addObject("tagName", tag)
        .addObject("operationListWithPath", openApiService.getOperationWithPathForTag(tag))
        .addObject("baseUrl", openApiService.getBaseUrl(request));
    return modelAndView;
  }

  private void getCommonModelAndViewAttributes(ModelAndView modelAndView, HttpServletRequest request) {
    modelAndView
        .addObject("tagLinks", openApiService.getTagLinks())
        .addObject("currentUrl", request.getRequestURI())
        .addObject("apiSpecUrl", openApiService.getApiSpecUrl(request));
  }


}
