package uk.gov.beis.els.categories.airconditioners.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.airconditioners.service.AirConditionersService;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class MultipleClimateGroupForm extends StandardTemplateForm50Char {

  @FieldPrompt("Is data available for warmer climate conditions?")
  @NotNull(message = "Select whether data is available for warmer climate conditions")
  private Boolean warmerConditions;

  @FieldPrompt("Energy efficiency class for Warmer heating season")
  @NotBlank(groups = WarmerClimateGroup.class, message = "Select an energy efficiency indicator for warmer climate conditions")
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String warmerHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in warmer climate conditions in kW")
  @NotNull(groups = WarmerClimateGroup.class)
  @Digits(groups = WarmerClimateGroup.class, integer = 2, fraction = 1, message = "Enter the design load for warmer climate conditions, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String warmerHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in warmer climate conditions (SCOP value)")
  @NotNull(groups = WarmerClimateGroup.class)
  @Digits(groups = WarmerClimateGroup.class, integer = 1, fraction = 1, message = "Enter the SCOP value for warmer climate conditions, 1 digit with an optional decimal place")
  @Schema(type = "number")
  private String warmerScop;

  @FieldPrompt("Annual energy consumption in kWh per year in warmer climate conditions")
  @NotNull(groups = WarmerClimateGroup.class)
  @Digits(groups = WarmerClimateGroup.class, integer = 4, fraction = 0,message = "Enter the annual energy consumption for warmer climate conditions, up to 4 digits")
  @Schema(type = "integer")
  private String warmerAnnualEnergyConsumption;

  @FieldPrompt("Is data available for colder climate conditions?")
  @NotNull(message = "Select whether data is available for colder climate conditions")
  private Boolean colderConditions;

  @FieldPrompt("Energy efficiency class for Colder heating season")
  @NotBlank(groups = ColderClimateGroup.class, message = "Select an energy efficiency indicator for colder climate conditions")
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String colderHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in colder climate conditions in kW")
  @NotNull(groups = ColderClimateGroup.class)
  @Digits(groups = ColderClimateGroup.class, integer = 2, fraction = 1, message = "Enter the design load for colder climate conditions, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  private String colderHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in colder climate conditions (SCOP value)")
  @NotNull(groups = ColderClimateGroup.class)
  @Digits(groups = ColderClimateGroup.class, integer = 1, fraction = 1, message = "Enter the SCOP value for colder climate conditions, 1 digit with an optional decimal place")
  @Schema(type = "number")
  private String colderScop;

  @FieldPrompt("Annual energy consumption in kWh per year in colder climate conditions")
  @NotNull(groups = ColderClimateGroup.class)
  @Digits(groups = ColderClimateGroup.class, integer = 4, fraction = 0,message = "Enter the annual energy consumption for colder climate conditions, up to 4 digits")
  @Schema(type = "integer")
  private String colderAnnualEnergyConsumption;


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
}
