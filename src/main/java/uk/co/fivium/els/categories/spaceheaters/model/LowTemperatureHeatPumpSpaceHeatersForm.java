package uk.co.fivium.els.categories.spaceheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class LowTemperatureHeatPumpSpaceHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("When was the product first placed on the market?")
  @NotBlank(message = "Specify when your product was first placed on the market")
  private String applicableLegislation;

  @FieldPrompt("The seasonal space heating energy efficiency class under average climate conditions for low temperature")
  @NotBlank(message = "Select an energy efficiency indicator")
  private String lowTempEfficiencyRating;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for colder climate conditions, up to 2 digits long")
  private String lowTempColderHeatOutput;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for average climate conditions, up to 2 digits long")
  private String lowTempAverageHeatOutput;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for warmer climate conditions, up to 2 digits long")
  private String lowTempWarmerHeatOutput;

  // TODO indicate optional on form, can be done by checking for a pattern annotation, or new '@Optional' one
  @FieldPrompt("Sound power level, indoors dB")
  @Pattern(regexp = "[0-9]{0,2}", message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power level, outdoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelOutdoors;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

  public String getLowTempEfficiencyRating() {
    return lowTempEfficiencyRating;
  }

  public void setLowTempEfficiencyRating(String lowTempEfficiencyRating) {
    this.lowTempEfficiencyRating = lowTempEfficiencyRating;
  }

  public String getLowTempColderHeatOutput() {
    return lowTempColderHeatOutput;
  }

  public void setLowTempColderHeatOutput(String lowTempColderHeatOutput) {
    this.lowTempColderHeatOutput = lowTempColderHeatOutput;
  }

  public String getLowTempAverageHeatOutput() {
    return lowTempAverageHeatOutput;
  }

  public void setLowTempAverageHeatOutput(String lowTempAverageHeatOutput) {
    this.lowTempAverageHeatOutput = lowTempAverageHeatOutput;
  }

  public String getLowTempWarmerHeatOutput() {
    return lowTempWarmerHeatOutput;
  }

  public void setLowTempWarmerHeatOutput(String lowTempWarmerHeatOutput) {
    this.lowTempWarmerHeatOutput = lowTempWarmerHeatOutput;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public String getSoundPowerLevelOutdoors() {
    return soundPowerLevelOutdoors;
  }

  public void setSoundPowerLevelOutdoors(String soundPowerLevelOutdoors) {
    this.soundPowerLevelOutdoors = soundPowerLevelOutdoors;
  }
}