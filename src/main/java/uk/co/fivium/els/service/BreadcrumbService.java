package uk.co.fivium.els.service;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.controller.ProductCategoryController;
import uk.co.fivium.els.mvc.ReverseRouter;

@Service
public class BreadcrumbService {

  private static final String BREADCRUMB_MODEL_AND_VIEW_ATTRIBUTE = "breadcrumbMap";
  private static final String GOVUK_SERVICE_HOMEPAGE_URL = "https://www.gov.uk/"; // TODO once provided


  /**
   * Add the breadcrumb model object to the ModelAndView, with the default
   * 'Home' and 'Product category' pages automatically added as the first two breadcrumbs.
   * @param modelAndView
   */
  public void addBreadcrumbToModel(ModelAndView modelAndView) {
    addBreadcrumbToModel(modelAndView, Collections.emptyMap());
  }

  /**
   * Add the breadcrumb model object to the ModelAndView, with the given breadcrumb.
   * Note the 'Home' and 'Product category' pages are automatically added as the first two breadcrumbs.
   * @param modelAndView The model and view to add the object to
   * @param text The text to be shown in the breadcrumb
   * @param route The url the breadcrumb should navigate to
   */
  public void addBreadcrumbToModel(ModelAndView modelAndView, String text, String route) {
    addBreadcrumbToModel(modelAndView, Collections.singletonMap(text, route));
  }

  /**
   * Add the breadcrumb model object to the ModelAndView, with the given last breadcrumb.
   * Note the 'Home' and 'Product category' pages are automatically added as the first two breadcrumbs.
   * @param modelAndView The model and view to add the object to
   * @param text The text to be shown in the breadcrumb
   */
  public void addLastBreadcrumbToModel(ModelAndView modelAndView, String text) {
    addBreadcrumbToModel(modelAndView, Collections.singletonMap(text, null));
  }

  /**
   * Add the breadcrumb model object to the ModelAndView, with the given breadcrumbs.
   * Note the 'Home' and 'Product category' pages are automatically added as the first two breadcrumbs.
   * @param modelAndView The model and view to add the object to
   * @param trail A map or breadcrumb text to route.
   */
  public void addBreadcrumbToModel(ModelAndView modelAndView, Map<String, String> trail) {
    Map<String, String> breadcrumbTrail = new LinkedHashMap<>();
    breadcrumbTrail.put("Home", GOVUK_SERVICE_HOMEPAGE_URL);
    breadcrumbTrail.put("Product category", ReverseRouter.route(on(ProductCategoryController.class).renderCategories(null)));
    breadcrumbTrail.putAll(trail);
    modelAndView.addObject(BREADCRUMB_MODEL_AND_VIEW_ATTRIBUTE, breadcrumbTrail);
  }

  /**
   * Push a breadcrumb item onto the 'stack'. addBreadcrumbToModel must be called first to initialise the model.
   * @param modelAndView The model and view to update
   * @param text The text to be shown in the breadcrumb
   * @param route The url the breadcrumb should navigate to
   */
  @SuppressWarnings("unchecked")
  public void pushBreadcrumb(ModelAndView modelAndView, String text, String route) {
    Object crumbMap = modelAndView.getModelMap().get(BREADCRUMB_MODEL_AND_VIEW_ATTRIBUTE);
    if (crumbMap instanceof LinkedHashMap) {
      ((LinkedHashMap) crumbMap).put(text, route);
    } else {
      throw new RuntimeException("Unable to push route onto breadcrumb stack. Stack not found in model");
    }
  }

  /**
   * Pushes the last breadcrumb onto the 'stack', which is not clickable so requires no route. addBreadcrumbToModel must be called first to initialise the model.
   * @param modelAndView The model and view to update
   * @param text The text to be shown in the breadcrumb
   */
  public void pushLastBreadcrumb(ModelAndView modelAndView, String text) {
    pushBreadcrumb(modelAndView, text, null);
  }

}