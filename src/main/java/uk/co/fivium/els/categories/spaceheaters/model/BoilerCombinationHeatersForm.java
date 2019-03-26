package uk.co.fivium.els.categories.spaceheaters.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

public class BoilerCombinationHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("When was the product first placed on the market?")
  @NotBlank(message = "Specify when your product was first placed on the market", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("The seasonal space heating energy efficiency class")
  @NotBlank(message = "Select a space heating energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The water heating energy efficiency class")
  @NotBlank(message = "Select a water heating energy efficiency indicator")
  private String waterHeatingEfficiencyRating;

  @FieldPrompt("The rated heat output in kW")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output, up to 2 digits long")
  private String heatOutput;

  @FieldPrompt("Sound power level, indoors dB (optional)")
  @Pattern(regexp = "[0-9]{0,2}", message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Can the heater be set to work only during off-peak hours?")
  @NotNull(message = "Specify if off-peak operation is supported")
  private Boolean offPeak;

  public String getDeclaredLoadProfile() {
    return declaredLoadProfile;
  }

  public void setDeclaredLoadProfile(String declaredLoadProfile) {
    this.declaredLoadProfile = declaredLoadProfile;
  }

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

  public String getWaterHeatingEfficiencyRating() {
    return waterHeatingEfficiencyRating;
  }

  public void setWaterHeatingEfficiencyRating(String waterHeatingEfficiencyRating) {
    this.waterHeatingEfficiencyRating = waterHeatingEfficiencyRating;
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

  public Boolean getOffPeak() {
    return offPeak;
  }

  public void setOffPeak(Boolean offPeak) {
    this.offPeak = offPeak;
  }
}
