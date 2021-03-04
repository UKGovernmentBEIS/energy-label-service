package uk.gov.beis.els.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticContentController {

  @GetMapping("/accessibility-statement")
  public ModelAndView renderAccessibilityStatement() {
    return new ModelAndView("staticContent/accessibilityStatement");
  }

  @GetMapping("/cookies")
  public ModelAndView renderCookiePolicy(
      @RequestHeader(value = "referer", required = false) final String referer,
      @Value("${app.valid_referer_domain}") String validRefererDomain
  ) {
    ModelAndView modelAndView = new ModelAndView("staticContent/cookiePolicy");
    if(referer != null && referer.startsWith(validRefererDomain)) {
      modelAndView.addObject("referer", referer);
    } else {
      modelAndView.addObject("referer", null);
    }
    return modelAndView;
  }

}
