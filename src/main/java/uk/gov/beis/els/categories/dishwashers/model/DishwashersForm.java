package uk.gov.beis.els.categories.dishwashers.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.dishwashers.model.validation.DishwashersFormSequenceProvider;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in dishwasher it doesn't have to be attached to the product, but it must still be easy to see. Old-style labels must be at least 110mm x 220mm when printed. New-style rescaled labels must be at least 96mm x 192mm when printed.")
@GroupSequenceProvider(DishwashersFormSequenceProvider.class)
public class DishwashersForm extends StandardTemplateForm30Char {

  @FieldPrompt("What kind of label do you need to create?")
  @NotBlank(message = "Select the kind of label you need to create", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re 1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emission, up to 2 digits long")
  private String noiseEmissions;

  // Pre march 2021 fields
  @FieldPrompt("Rated capacity (number of standard place settings) for the standard cleaning cycle")
  @Digits(integer = 2, fraction = 0, message = "Enter a rated capacity, up to 2 digits long", groups = PreMarch2021Field.class)
  private String standardCapacity;

  @FieldPrompt("Annual energy consumption (AEC) in kWh per year")
  @Digits(integer = 3, fraction = 0, message = "Enter an annual energy consumption, up to 3 digits long", groups = PreMarch2021Field.class)
  private String annualEnergyConsumption;

  @FieldPrompt("Annual water consumption (AWC) in litres per year")
  @Digits(integer = 4, fraction = 0, message = "Enter an annual water consumption, up to 4 digits long", groups = PreMarch2021Field.class)
  private String annualWaterConsumption;

  @FieldPrompt("Drying efficiency class")
  @NotBlank(message = "Select a drying efficiency class", groups = PreMarch2021Field.class)
  private String dryingEfficiencyRating;

  // Post march 2021 fields
  @FieldPrompt("Rated capacity (number of standard place settings) for the eco programme")
  @Digits(integer = 2, fraction = 0, message = "Enter a rated capacity, up to 2 digits long", groups = PostMarch2021Field.class)
  private String ecoCapacity;

  @FieldPrompt("Eco programme energy consumption (EPEC) in kWh per 100 cycles")
  @Digits(integer = 3, fraction = 0, message = "Enter an eco programme energy consumption, up to 3 digits long", groups = PostMarch2021Field.class)
  private String energyConsumptionPer100Cycles;

  @FieldPrompt("Eco programme water consumption (EPWC) in litres per cycle")
  @Digits(integer = 2, fraction = 1, message = "Enter an eco programme water consumption, up to 2 digits with an optional decimal place", groups = PostMarch2021Field.class)
  private String waterConsumptionPerCycle;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the eco programme duration, up to 1 digit. If the eco programme duration is under 1 hour, enter 0", groups = PostMarch2021Field.class)
  private String programmeDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the eco programme duration, between 0 and 59", groups = PostMarch2021Field.class)
  private String programmeDurationMinutes;

  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class", groups = PostMarch2021Field.class)
  private String noiseEmissionsClass;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

  public String getAnnualEnergyConsumption() {
    return annualEnergyConsumption;
  }

  public void setAnnualEnergyConsumption(String annualEnergyConsumption) {
    this.annualEnergyConsumption = annualEnergyConsumption;
  }

  public String getAnnualWaterConsumption() {
    return annualWaterConsumption;
  }

  public void setAnnualWaterConsumption(String annualWaterConsumption) {
    this.annualWaterConsumption = annualWaterConsumption;
  }

  public String getDryingEfficiencyRating() {
    return dryingEfficiencyRating;
  }

  public void setDryingEfficiencyRating(String dryingEfficiencyRating) {
    this.dryingEfficiencyRating = dryingEfficiencyRating;
  }

  public String getEcoCapacity() {
    return ecoCapacity;
  }

  public void setEcoCapacity(String ecoCapacity) {
    this.ecoCapacity = ecoCapacity;
  }

  public String getEnergyConsumptionPer100Cycles() {
    return energyConsumptionPer100Cycles;
  }

  public void setEnergyConsumptionPer100Cycles(String energyConsumptionPer100Cycles) {
    this.energyConsumptionPer100Cycles = energyConsumptionPer100Cycles;
  }

  public String getWaterConsumptionPerCycle() {
    return waterConsumptionPerCycle;
  }

  public void setWaterConsumptionPerCycle(String waterConsumptionPerCycle) {
    this.waterConsumptionPerCycle = waterConsumptionPerCycle;
  }

  public String getProgrammeDurationHours() {
    return programmeDurationHours;
  }

  public void setProgrammeDurationHours(String programmeDurationHours) {
    this.programmeDurationHours = programmeDurationHours;
  }

  public String getProgrammeDurationMinutes() {
    return programmeDurationMinutes;
  }

  public void setProgrammeDurationMinutes(String programmeDurationMinutes) {
    this.programmeDurationMinutes = programmeDurationMinutes;
  }

  public String getNoiseEmissionsClass() {
    return noiseEmissionsClass;
  }

  public void setNoiseEmissionsClass(String noiseEmissionsClass) {
    this.noiseEmissionsClass = noiseEmissionsClass;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getStandardCapacity() {
    return standardCapacity;
  }

  public void setStandardCapacity(String standardCapacity) {
    this.standardCapacity = standardCapacity;
  }

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }
}
