package uk.gov.beis.els.categories.spaceheaters.model;

import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
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
