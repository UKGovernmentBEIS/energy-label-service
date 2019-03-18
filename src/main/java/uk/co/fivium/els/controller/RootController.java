package uk.co.fivium.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;

@Controller
public class RootController {

  private final BreadcrumbService breadcrumbService;

  @Autowired
  public RootController(BreadcrumbService breadcrumbService) {
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderStartPage() {
    ModelAndView modelAndView = new ModelAndView("startPage");
    return modelAndView;
  }
// TODO Revert back to redirecting to categories once we don't need the temporary start page
//  public ModelAndView redirectToCategories() {
//    return ReverseRouter.redirect(on(ProductCategoryController.class).renderCategories(null));
//  }

  // TODO remove route and template once all categories are completed
  @GetMapping("/not-yet-implemented")
  public ModelAndView renderNotYetImplemented() {
    ModelAndView modelAndView = new ModelAndView("notYetImplemented");
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Not available");
    return modelAndView;
  }


}
