package uk.co.fivium.els.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AnalyticsControllerAdvice {

  private final boolean googleAnalyticsEnabled;
  private final String googleAnalyticsToken;


  public AnalyticsControllerAdvice(@Value("${app.enable_google_analytics}") boolean googleAnalyticsEnabled, @Value("${app.google_analytics_token}") String googleAnalyticsToken) {
    this.googleAnalyticsEnabled = googleAnalyticsEnabled;
    this.googleAnalyticsToken = googleAnalyticsToken;
  }

  @ModelAttribute
  public void addCommonModelAttributes(Model model) {
    model.addAttribute("googleAnalyticsEnabled", googleAnalyticsEnabled);
    model.addAttribute("googleAnalyticsToken", googleAnalyticsToken);
  }

}
