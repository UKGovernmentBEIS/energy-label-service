package uk.co.fivium.els.categories.spaceheaters.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class HeatPumpSpaceHeatersForm extends LowTemperatureHeatPumpSpaceHeatersForm {

  @FieldPrompt("The seasonal space heating energy efficiency class under average climate conditions for medium temperature")
  @NotBlank(message = "Select an energy efficiency indicator")
  private String mediumTempEfficiencyRating;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for colder climate conditions, up to 2 digits long")
  private String mediumTempColderHeatOutput;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for average climate conditions, up to 2 digits long")
  private String mediumTempAverageHeatOutput;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for warmer climate conditions, up to 2 digits long")
  private String mediumTempWarmerHeatOutput;

  public String getMediumTempEfficiencyRating() {
    return mediumTempEfficiencyRating;
  }

  public void setMediumTempEfficiencyRating(String mediumTempEfficiencyRating) {
    this.mediumTempEfficiencyRating = mediumTempEfficiencyRating;
  }

  public String getMediumTempColderHeatOutput() {
    return mediumTempColderHeatOutput;
  }

  public void setMediumTempColderHeatOutput(String mediumTempColderHeatOutput) {
    this.mediumTempColderHeatOutput = mediumTempColderHeatOutput;
  }

  public String getMediumTempAverageHeatOutput() {
    return mediumTempAverageHeatOutput;
  }

  public void setMediumTempAverageHeatOutput(String mediumTempAverageHeatOutput) {
    this.mediumTempAverageHeatOutput = mediumTempAverageHeatOutput;
  }

  public String getMediumTempWarmerHeatOutput() {
    return mediumTempWarmerHeatOutput;
  }

  public void setMediumTempWarmerHeatOutput(String mediumTempWarmerHeatOutput) {
    this.mediumTempWarmerHeatOutput = mediumTempWarmerHeatOutput;
  }
}
