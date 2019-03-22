package uk.co.fivium.els.categories.spaceheaters.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

public class CogenerationSpaceHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("When was the product first placed on the market?")
  @NotBlank(message = "Specify when your product was first placed on the market", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("The seasonal space heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The rated heat output in kW")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output, up to 2 digits long")
  private String heatOutput;

  @FieldPrompt("Sound power level, indoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Is there an additional electricity generation function?")
  @NotNull(message = "Select whether there is an additional electricity generation function")
  private Boolean electricityGeneration;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getHeatOutput() {
    return heatOutput;
  }

  public void setHeatOutput(String heatOutput) {
    this.heatOutput = heatOutput;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public Boolean getElectricityGeneration() {
    return electricityGeneration;
  }

  public void setElectricityGeneration(Boolean electricityGeneration) {
    this.electricityGeneration = electricityGeneration;
  }
}
