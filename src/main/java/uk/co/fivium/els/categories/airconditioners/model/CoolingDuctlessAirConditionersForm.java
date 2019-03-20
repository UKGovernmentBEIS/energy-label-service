package uk.co.fivium.els.categories.airconditioners.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class CoolingDuctlessAirConditionersForm extends StandardTemplateForm50Char {

  @FieldPrompt("Energy efficiency class for cooling")
  @NotBlank(message = "Select an energy efficiency indicator for cooling")
  private String coolingEfficiencyRating;

  @FieldPrompt("Cooling mode: design load in kW")
  @Digits(integer = 2, fraction = 1, message = "Enter the design load in cooling mode, up to 2 digits with an optional decimal place")
  private String coolingModeDesignLoad;

  @FieldPrompt("Cooling mode: seasonal energy efficiency ratio (SEER value) ")
  @Digits(integer = 1, fraction = 1, message = "Enter the design load in cooling mode, 1 digit with an optional decimal place")
  private String coolingModeSeer;

  @FieldPrompt("Annual energy consumption in kWh per year, for cooling")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  private String coolingAnnualEnergyConsumption;

  @FieldPrompt("Sound power levels for indoor units expressed in dB(A) re1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power levels for outdoor units expressed in dB(A) re1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelOutdoors;

  public String getCoolingEfficiencyRating() {
    return coolingEfficiencyRating;
  }

  public void setCoolingEfficiencyRating(String coolingEfficiencyRating) {
    this.coolingEfficiencyRating = coolingEfficiencyRating;
  }

  public String getCoolingModeDesignLoad() {
    return coolingModeDesignLoad;
  }

  public void setCoolingModeDesignLoad(String coolingModeDesignLoad) {
    this.coolingModeDesignLoad = coolingModeDesignLoad;
  }

  public String getCoolingModeSeer() {
    return coolingModeSeer;
  }

  public void setCoolingModeSeer(String coolingModeSeer) {
    this.coolingModeSeer = coolingModeSeer;
  }

  public String getCoolingAnnualEnergyConsumption() {
    return coolingAnnualEnergyConsumption;
  }

  public void setCoolingAnnualEnergyConsumption(String coolingAnnualEnergyConsumption) {
    this.coolingAnnualEnergyConsumption = coolingAnnualEnergyConsumption;
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
