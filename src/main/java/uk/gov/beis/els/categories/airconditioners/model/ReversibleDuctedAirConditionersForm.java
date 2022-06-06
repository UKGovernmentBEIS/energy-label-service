package uk.gov.beis.els.categories.airconditioners.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.airconditioners.service.AirConditionersService;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Reversible single or double duct air conditioner energy label")
public class ReversibleDuctedAirConditionersForm extends StandardTemplateForm50Char {

  @FieldPrompt("Energy efficiency class for cooling")
  @NotBlank(message = "Select an energy efficiency indicator for cooling", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String coolingEfficiencyRating;

  @FieldPrompt("Rated capacity for cooling in kW")
  @NotNull
  @Digits(integer = 2, fraction = 1, message = "Enter the rated capacity for cooling, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String coolingKw;

  @FieldPrompt("EER rated value")
  @NotNull
  @Digits(integer = 1, fraction = 1, message = "Enter the EER rated value, 1 digit with an optional decimal place")
  @Schema(type = "number")
  private String eerRated;

  @FieldPrompt("Hourly energy consumption for cooling in kWh per 60 minutes, rounded up to the nearest integer")
  @NotNull
  @Digits(integer = 2, fraction = 0, message = "Enter the hourly energy consumption for cooling, up to 2 digits long")
  @Schema(type = "integer")
  private String coolingHourlyEnergyConsumption;

  @FieldPrompt("Energy efficiency class for heating")
  @NotBlank(message = "Select an energy efficiency indicator for heating")
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String heatingEfficiencyRating;

  @FieldPrompt("Rated capacity for heating in kW")
  @NotNull
  @Digits(integer = 2, fraction = 1, message = "Enter the rated capacity for heating, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String heatingKw;

  @FieldPrompt("COPrated value")
  @NotNull
  @Digits(integer = 1, fraction = 1, message = "Enter the COPrated value, 1 digit with an optional decimal place")
  @Schema(type = "number")
  private String copRated;

  @FieldPrompt("Hourly energy consumption for heating in kWh per 60 minutes, rounded up to the nearest integer")
  @NotNull
  @Digits(integer = 2, fraction = 0, message = "Enter the hourly energy consumption for heating, up to 2 digits long")
  @Schema(type = "integer")
  private String heatingHourlyEnergyConsumption;

  @FieldPrompt("Sound power levels for indoor units expressed in dB(A) re 1 pW, rounded to the nearest integer")
  @NotNull
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  @Schema(type = "integer")
  private String soundPowerLevelIndoors;

  public String getCoolingEfficiencyRating() {
    return coolingEfficiencyRating;
  }

  public void setCoolingEfficiencyRating(String coolingEfficiencyRating) {
    this.coolingEfficiencyRating = coolingEfficiencyRating;
  }

  public String getCoolingKw() {
    return coolingKw;
  }

  public void setCoolingKw(String coolingKw) {
    this.coolingKw = coolingKw;
  }

  public String getEerRated() {
    return eerRated;
  }

  public void setEerRated(String eerRated) {
    this.eerRated = eerRated;
  }

  public String getCoolingHourlyEnergyConsumption() {
    return coolingHourlyEnergyConsumption;
  }

  public void setCoolingHourlyEnergyConsumption(String coolingHourlyEnergyConsumption) {
    this.coolingHourlyEnergyConsumption = coolingHourlyEnergyConsumption;
  }

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
