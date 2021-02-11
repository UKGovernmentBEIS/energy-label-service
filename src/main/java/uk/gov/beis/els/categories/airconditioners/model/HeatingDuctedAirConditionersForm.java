package uk.gov.beis.els.categories.airconditioners.model;

import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

public class HeatingDuctedAirConditionersForm extends StandardTemplateForm50Char {

  @FieldPrompt("Energy efficiency class for heating")
  @NotBlank(message = "Select an energy efficiency indicator for heating", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String heatingEfficiencyRating;

  @FieldPrompt("Rated capacity for heating in kW")
  @Digits(integer = 2, fraction = 1, message = "Enter the rated capacity for heating, up to 2 digits with an optional decimal place")
  private String heatingKw;

  @FieldPrompt("COPrated value")
  @Digits(integer = 1, fraction = 1, message = "Enter the COPrated value, 1 digit with an optional decimal place")
  private String copRated;

  @FieldPrompt("Hourly energy consumption in kWh per 60 minutes, rounded up to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the hourly energy consumption, up to 2 digits long")
  private String heatingHourlyEnergyConsumption;

  @FieldPrompt("Sound power levels for indoor units expressed in dB(A) re 1 pW, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  public String getHeatingEfficiencyRating() {
    return heatingEfficiencyRating;
  }

  public void setHeatingEfficiencyRating(String heatingEfficiencyRating) {
    this.heatingEfficiencyRating = heatingEfficiencyRating;
  }

  public String getHeatingKw() {
    return heatingKw;
  }

  public void setHeatingKw(String heatingKw) {
    this.heatingKw = heatingKw;
  }

  public String getCopRated() {
    return copRated;
  }

  public void setCopRated(String copRated) {
    this.copRated = copRated;
  }

  public String getHeatingHourlyEnergyConsumption() {
    return heatingHourlyEnergyConsumption;
  }

  public void setHeatingHourlyEnergyConsumption(String heatingHourlyEnergyConsumption) {
    this.heatingHourlyEnergyConsumption = heatingHourlyEnergyConsumption;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }
}
