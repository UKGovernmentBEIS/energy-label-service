package uk.co.fivium.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.service.BreadcrumbService;

@Controller
public class RootController {

  private final BreadcrumbService breadcrumbService;

  @Value("${app.show_start_page}")
  private boolean startPageEnabled;

  @Autowired
  public RootController(BreadcrumbService breadcrumbService) {
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderRootPage() {
    if (startPageEnabled) {
      return new ModelAndView("startPage");
    } else {
      return ReverseRouter.redirect(on(UkOrEuController.class).renderUkOrEu(null));
    }
  }

  // TODO remove route and template once all categories are completed
  @GetMapping("/not-yet-implemented")
  public ModelAndView renderNotYetImplemented() {
    ModelAndView modelAndView = new ModelAndView("notYetImplemented");
    breadcrumbService.addLastBreadcrumbToModel(modelAndView, "Not available");
    return modelAndView;
  }


}
