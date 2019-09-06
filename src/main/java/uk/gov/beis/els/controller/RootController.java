package uk.gov.beis.els.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.beis.els.mvc.ReverseRouter;

@Controller
public class RootController {

  @Value("${app.show_start_page}")
  private boolean startPageEnabled;

  @GetMapping("/")
  public ModelAndView renderRootPage(@RequestParam(name = "_ga", required = false) String govUkAnalyticsId) {
    if (startPageEnabled) {
      return new ModelAndView("startPage");
    } else {
      // The _ga param from gov.uk needs to be preserved for the first redirect, after which is will get picked up the GA js
      return ReverseRouter.redirectWithQueryParams(
          on(ProductCategoryController.class).renderCategories(null),
          (govUkAnalyticsId == null) ? Collections.emptyMap() : Collections.singletonMap("_ga", govUkAnalyticsId)
      );
    }
  }

}
