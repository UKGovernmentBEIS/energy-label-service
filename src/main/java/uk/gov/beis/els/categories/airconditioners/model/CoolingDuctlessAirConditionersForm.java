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

@Schema(name = "Cooling-only ductless air conditioner energy label")
public class CoolingDuctlessAirConditionersForm extends StandardTemplateForm50Char {

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

  @FieldPrompt("Cooling mode: seasonal energy efficiency ratio (SEER value)")
  @NotNull
  @Digits(integer = 2, fraction = 1, message = "Enter the design load in cooling mode, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String coolingModeSeer;

  @FieldPrompt("Annual energy consumption in kWh per year, for cooling")
  @NotNull
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  @Schema(type = "integer")
  private String coolingAnnualEnergyConsumption;

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
