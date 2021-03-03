package uk.gov.beis.els.controller;

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
  public ModelAndView renderCookiePolicy(@RequestHeader(value = "referer", required = false) final String referer) {
    ModelAndView modelAndView = new ModelAndView("staticContent/cookiePolicy");
    modelAndView.addObject("referer", referer);
    return modelAndView;
  }

}
