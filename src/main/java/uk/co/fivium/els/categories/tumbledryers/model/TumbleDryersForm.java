package uk.co.fivium.els.categories.tumbledryers.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class TumbleDryersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class of the tumble dryer")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Weighted annual energy consumption (AEC) in kWh per year, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
  private String energyConsumption;

  @FieldPrompt("Cycle time corresponding to the standard cotton programme at full load in minutes per cycle")
  @Digits(integer = 3, fraction = 0, message = "Enter the cycle time in minutes, up to 3 digits long")
  private String cycleTime;

  @FieldPrompt("Rated capacity, for the standard cotton programme at full load in kg")
  @Digits(integer = 1, fraction = 1, message = "Enter the capacity, as 1 digit and with an optional decimal place")
  private String ratedCapacity;

  @FieldPrompt("Sound power level (weighted average value — LWA), during the drying phase, for the standard cotton programme at full load, expressed in dB ")
  @Digits(integer = 2, fraction = 0, message = "Enter the sound power level, up to 2 digits long")
  private String soundPowerLevel;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getEnergyConsumption() {
    return energyConsumption;
  }

  public void setEnergyConsumption(String energyConsumption) {
    this.energyConsumption = energyConsumption;
  }

  public String getCycleTime() {
    return cycleTime;
  }

  public void setCycleTime(String cycleTime) {
    this.cycleTime = cycleTime;
  }

  public String getRatedCapacity() {
    return ratedCapacity;
  }

  public void setRatedCapacity(String ratedCapacity) {
    this.ratedCapacity = ratedCapacity;
  }

  public String getSoundPowerLevel() {
    return soundPowerLevel;
  }

  public void setSoundPowerLevel(String soundPowerLevel) {
    this.soundPowerLevel = soundPowerLevel;
  }

}