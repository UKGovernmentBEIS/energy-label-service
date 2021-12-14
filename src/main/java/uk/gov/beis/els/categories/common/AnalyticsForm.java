package uk.gov.beis.els.categories.common;

import io.swagger.annotations.ApiModelProperty;

public class AnalyticsForm {

  @ApiModelProperty(hidden = true)
  private String googleAnalyticsClientId;

  public String getGoogleAnalyticsClientId() {
    return googleAnalyticsClientId;
  }

  public void setGoogleAnalyticsClientId(String googleAnalyticsClientId) {
    this.googleAnalyticsClientId = googleAnalyticsClientId;
  }
}
