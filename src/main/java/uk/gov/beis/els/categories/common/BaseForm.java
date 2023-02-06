package uk.gov.beis.els.categories.common;

import io.swagger.v3.oas.annotations.media.Schema;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.model.EnergyLabelFormat;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class BaseForm {

  @Schema(hidden = true)
  private String googleAnalyticsClientId;

  @FieldPrompt("Label format")
  @LabelFormatConstraint(message = "Select a label format")
  @Schema(description = "Format of the generated label. Optional, defaults to PDF if not set.")
  @ApiValuesFromEnum(value = EnergyLabelFormat.class)
  private String outputFormat;

  public String getGoogleAnalyticsClientId() {
    return googleAnalyticsClientId;
  }

  public void setGoogleAnalyticsClientId(String googleAnalyticsClientId) {
    this.googleAnalyticsClientId = googleAnalyticsClientId;
  }

  public String getOutputFormat() {
    return outputFormat;
  }

  public void setOutputFormat(String outputFormat) {
    this.outputFormat = outputFormat;
  }
}
