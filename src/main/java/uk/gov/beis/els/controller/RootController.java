package uk.gov.beis.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.mvc.ReverseRouter;

@Controller
public class RootController {

  @Value("${app.show_start_page}")
  private boolean startPageEnabled;

  @GetMapping("/")
  public ModelAndView renderRootPage() {
    if (startPageEnabled) {
      return new ModelAndView("startPage");
    } else {
      return ReverseRouter.redirect(on(ProductCategoryController.class).renderCategories(null));
    }
  }

}
