package uk.gov.beis.els.categories.common;

import io.swagger.v3.oas.annotations.media.Schema;

public class AnalyticsForm {

  @Schema(hidden = true)
  private String googleAnalyticsClientId;

  public String getGoogleAnalyticsClientId() {
    return googleAnalyticsClientId;
  }

  public void setGoogleAnalyticsClientId(String googleAnalyticsClientId) {
    this.googleAnalyticsClientId = googleAnalyticsClientId;
  }
}
