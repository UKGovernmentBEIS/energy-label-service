package uk.co.fivium.els.categories.airconditioners.model;

import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@GroupSequenceProvider(ReversibleDuctlessAirConditionersFormSequenceProvider.class)
public class ReversibleDuctlessAirConditionersForm extends StandardTemplateForm50Char {

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

  @FieldPrompt("Energy efficiency class for Average heating season")
  @NotBlank(message = "Select an energy efficiency indicator for average climate conditions")
  private String averageHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in average climate conditions in kW")
  @Digits(integer = 2, fraction = 1, message = "Enter the design load for average climate conditions, up to 2 digits with an optional decimal place")
  private String averageHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in average climate conditions (SCOP value)")
  @Digits(integer = 1, fraction = 1, message = "Enter the SCOP value for average climate conditions, 1 digit with an optional decimal place")
  private String averageScop;

  @FieldPrompt("Annual energy consumption in kWh per year in average climate conditions")
  @Digits(integer = 4, fraction = 0,message = "Enter the annual energy consumption for average climate conditions, up to 4 digits")
  private String averageAnnualEnergyConsumption;

  @FieldPrompt("Is data available for warmer climate conditons?")
  @NotNull(message = "Select whether data is available for warmer climate conditions")
  private Boolean warmerConditions;

  @FieldPrompt("Energy efficiency class for Warmer heating season")
  @NotBlank(groups = WarmerClimateGroup.class, message = "Select an energy efficiency indicator for warmer climate conditions")
  private String warmerHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in warmer climate conditions in kW")
  @Digits(groups = WarmerClimateGroup.class, integer = 2, fraction = 1, message = "Enter the design load for warmer climate conditions, up to 2 digits with an optional decimal place")
  private String warmerHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in warmer climate conditions (SCOP value)")
  @Digits(groups = WarmerClimateGroup.class, integer = 1, fraction = 1, message = "Enter the SCOP value for warmer climate conditions, 1 digit with an optional decimal place")
  private String warmerScop;

  @FieldPrompt("Annual energy consumption in kWh per year in warmer climate conditions")
  @Digits(groups = WarmerClimateGroup.class, integer = 4, fraction = 0,message = "Enter the annual energy consumption for warmer climate conditions, up to 4 digits")
  private String warmerAnnualEnergyConsumption;

  @FieldPrompt("Is data available for colder climate conditons?")
  @NotNull(message = "Select whether data is available for colder climate conditions")
  private Boolean colderConditions;

  @FieldPrompt("Energy efficiency class for Colder heating season")
  @NotBlank(groups = WarmerClimateGroup.class, message = "Select an energy efficiency indicator for colder climate conditions")
  private String colderHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in colder climate conditions in kW")
  @Digits(groups = WarmerClimateGroup.class, integer = 2, fraction = 1, message = "Enter the design load for colder climate conditions, up to 2 digits with an optional decimal place")
  private String colderHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in colder climate conditions (SCOP value)")
  @Digits(groups = WarmerClimateGroup.class, integer = 1, fraction = 1, message = "Enter the SCOP value for colder climate conditions, 1 digit with an optional decimal place")
  private String colderScop;

  @FieldPrompt("Annual energy consumption in kWh per year in colder climate conditions")
  @Digits(groups = WarmerClimateGroup.class, integer = 4, fraction = 0,message = "Enter the annual energy consumption for colder climate conditions, up to 4 digits")
  private String colderAnnualEnergyConsumption;

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

  public String getAverageHeatingEfficiencyRating() {
    return averageHeatingEfficiencyRating;
  }

  public void setAverageHeatingEfficiencyRating(String averageHeatingEfficiencyRating) {
    this.averageHeatingEfficiencyRating = averageHeatingEfficiencyRating;
  }

  public String getAverageHeatingDesignLoad() {
    return averageHeatingDesignLoad;
  }

  public void setAverageHeatingDesignLoad(String averageHeatingDesignLoad) {
    this.averageHeatingDesignLoad = averageHeatingDesignLoad;
  }

  public String getAverageScop() {
    return averageScop;
  }

  public void setAverageScop(String averageScop) {
    this.averageScop = averageScop;
  }

  public String getAverageAnnualEnergyConsumption() {
    return averageAnnualEnergyConsumption;
  }

  public void setAverageAnnualEnergyConsumption(String averageAnnualEnergyConsumption) {
    this.averageAnnualEnergyConsumption = averageAnnualEnergyConsumption;
  }

  public Boolean getWarmerConditions() {
    return warmerConditions;
  }

  public void setWarmerConditions(Boolean warmerConditions) {
    this.warmerConditions = warmerConditions;
  }

  public String getWarmerHeatingEfficiencyRating() {
    return warmerHeatingEfficiencyRating;
  }

  public void setWarmerHeatingEfficiencyRating(String warmerHeatingEfficiencyRating) {
    this.warmerHeatingEfficiencyRating = warmerHeatingEfficiencyRating;
  }

  public String getWarmerHeatingDesignLoad() {
    return warmerHeatingDesignLoad;
  }

  public void setWarmerHeatingDesignLoad(String warmerHeatingDesignLoad) {
    this.warmerHeatingDesignLoad = warmerHeatingDesignLoad;
  }

  public String getWarmerScop() {
    return warmerScop;
  }

  public void setWarmerScop(String warmerScop) {
    this.warmerScop = warmerScop;
  }

  public String getWarmerAnnualEnergyConsumption() {
    return warmerAnnualEnergyConsumption;
  }

  public void setWarmerAnnualEnergyConsumption(String warmerAnnualEnergyConsumption) {
    this.warmerAnnualEnergyConsumption = warmerAnnualEnergyConsumption;
  }

  public Boolean getColderConditions() {
    return colderConditions;
  }

  public void setColderConditions(Boolean colderConditions) {
    this.colderConditions = colderConditions;
  }

  public String getColderHeatingEfficiencyRating() {
    return colderHeatingEfficiencyRating;
  }

  public void setColderHeatingEfficiencyRating(String colderHeatingEfficiencyRating) {
    this.colderHeatingEfficiencyRating = colderHeatingEfficiencyRating;
  }

  public String getColderHeatingDesignLoad() {
    return colderHeatingDesignLoad;
  }

  public void setColderHeatingDesignLoad(String colderHeatingDesignLoad) {
    this.colderHeatingDesignLoad = colderHeatingDesignLoad;
  }

  public String getColderScop() {
    return colderScop;
  }

  public void setColderScop(String colderScop) {
    this.colderScop = colderScop;
  }

  public String getColderAnnualEnergyConsumption() {
    return colderAnnualEnergyConsumption;
  }

  public void setColderAnnualEnergyConsumption(String colderAnnualEnergyConsumption) {
    this.colderAnnualEnergyConsumption = colderAnnualEnergyConsumption;
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
