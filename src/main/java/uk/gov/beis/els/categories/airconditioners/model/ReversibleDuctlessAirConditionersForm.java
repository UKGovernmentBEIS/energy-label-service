package uk.gov.beis.els.categories.airconditioners.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.airconditioners.service.AirConditionersService;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("The label must be at least 120mm x 210mm when printed")
@GroupSequenceProvider(ReversibleDuctlessAirConditionersFormSequenceProvider.class)
public class ReversibleDuctlessAirConditionersForm extends MultipleClimateGroupForm {

  @FieldPrompt("Energy efficiency class for cooling")
  @NotBlank(message = "Select an energy efficiency indicator for cooling", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String coolingEfficiencyRating;

  @FieldPrompt("Cooling mode: design load in kW")
  @NotNull
  @Digits(integer = 2, fraction = 1, message = "Enter the design load in cooling mode, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String coolingModeDesignLoad;

  @FieldPrompt("Cooling mode: seasonal energy efficiency ratio (SEER value) ")
  @NotNull
  @Digits(integer = 2, fraction = 1, message = "Enter the design load in cooling mode, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String coolingModeSeer;

  @FieldPrompt("Annual energy consumption in kWh per year, for cooling")
  @NotNull
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  @Schema(type = "integer")
  private String coolingAnnualEnergyConsumption;

  @FieldPrompt("Energy efficiency class for Average heating season")
  @NotBlank(message = "Select an energy efficiency indicator for average climate conditions")
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String averageHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in average climate conditions in kW")
  @NotNull
  @Digits(integer = 2, fraction = 1, message = "Enter the design load for average climate conditions, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String averageHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in average climate conditions (SCOP value)")
  @NotNull
  @Digits(integer = 1, fraction = 1, message = "Enter the SCOP value for average climate conditions, 1 digit with an optional decimal place")
  @Schema(type = "number")
  private String averageScop;

  @FieldPrompt("Annual energy consumption in kWh per year in average climate conditions")
  @NotNull
  @Digits(integer = 4, fraction = 0,message = "Enter the annual energy consumption for average climate conditions, up to 4 digits")
  @Schema(type = "integer")
  private String averageAnnualEnergyConsumption;

  @FieldPrompt("Sound power levels for indoor units expressed in dB(A) re 1 pW")
  @NotNull
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  @Schema(type = "integer")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power levels for outdoor units expressed in dB(A) re 1 pW")
  @NotNull
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  @Schema(type = "integer")
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
