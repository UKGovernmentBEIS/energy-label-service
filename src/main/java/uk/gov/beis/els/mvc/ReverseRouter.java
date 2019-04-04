package uk.gov.beis.els.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class ReverseRouter {

  private ReverseRouter() {
  }

  public static String route(Object methodCall) {
    return route(methodCall, Collections.emptyMap(), true);
  }

  public static String route(Object methodCall, Map<String, Object> uriVariables) {
    return route(methodCall, uriVariables, true);
  }

  @SuppressWarnings("unchecked")
  public static String route(Object methodCall, Map<String, Object> uriVariables,
                             boolean expandUriVariablesFromRequest) {
    //Establish URI variables to substitute - explicitly provided should take precedence
    Map<String, Object> allUriVariables = new HashMap<>();
    if (expandUriVariablesFromRequest) {
      RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
      if (requestAttributes != null) {
        Map<String, Object> requestAttributeMap = (Map<String, Object>) requestAttributes.getAttribute(
            HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

        if (requestAttributeMap != null) {
          allUriVariables.putAll(requestAttributeMap);
        }
      }
    }

    allUriVariables.putAll(uriVariables);

    //Use a UriComponentsBuilder which is not scoped to the request to get relative URIs (instead of absolute)
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
    return MvcUriComponentsBuilder.fromMethodCall(builder, methodCall).buildAndExpand(allUriVariables).toUriString();
  }

  public static ModelAndView redirect(Object methodCall) {
    return redirect(methodCall, Collections.emptyMap());
  }

  public static ModelAndView redirect(Object methodCall, Map<String, Object> uriVariables) {
    return redirect(methodCall, uriVariables, true);
  }

  public static ModelAndView redirect(Object methodCall, Map<String, Object> uriVariables,
                                      boolean expandUriVariablesFromRequest) {
    return new ModelAndView("redirect:" + route(methodCall, uriVariables, expandUriVariablesFromRequest));
  }

  /**
   * Return an empty BindingResult.
   * Used to avoid passing null into a BindingResult route parameter, which then will cause null warnings to be thrown by IJ if
   * the controller invokes methods on the BindingResult, as it now thinks null is a possible runtime value.
   * @return An empty BindingResult
   */
  public static BindingResult emptyBindingResult() {
    return new BeanPropertyBindingResult(null, "empty");
  }
}
