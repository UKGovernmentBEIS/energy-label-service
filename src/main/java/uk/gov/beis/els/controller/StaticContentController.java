package uk.gov.beis.els.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticContentController {

  @GetMapping("/accessibility-statement")
  public ModelAndView renderAccessibilityStatement() {
    return new ModelAndView("staticContent/accessibilityStatement");
  }

}
