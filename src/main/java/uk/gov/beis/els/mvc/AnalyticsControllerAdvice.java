package uk.gov.beis.els.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AnalyticsControllerAdvice {

  private final boolean googleAnalyticsEnabled;


  public AnalyticsControllerAdvice(@Value("${app.enable_google_analytics}") boolean googleAnalyticsEnabled) {
    this.googleAnalyticsEnabled = googleAnalyticsEnabled;
  }

  @ModelAttribute
  public void addCommonModelAttributes(Model model) {
    model.addAttribute("googleAnalyticsEnabled", googleAnalyticsEnabled);
  }

}
