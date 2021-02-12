package uk.gov.beis.els.categories.washingmachines.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.washingmachines.model.validation.WashingMachineFormSequenceProvider;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in washing machine it doesn't have to be attached to the product, but it must still be easy to see. Original-style labels must be at least 110mm x 220mm when printed. New-style 'rescaled' labels must be at least 96mm x 192mm when printed.")
@GroupSequenceProvider(WashingMachineFormSequenceProvider.class)
public class WashingMachinesForm extends StandardTemplateForm30Char {

  @FieldPrompt("What kind of label do you need to create?")
  @NotBlank(message = "Select the kind of label you need to create", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Spin-drying efficiency class")
  @NotBlank(message = "Select a spin-drying efficiency class")
  private String spinDryingEfficiencyRating;

  // Pre march 2021 fields
  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter a weighted energy consumption, up to 3 digits long", groups = PreMarch2021Field.class)
  private String annualEnergyConsumption;

  @FieldPrompt("Weighted annual water consumption (AWC) in litres per year, rounded to the nearest integer [L/annum]")
  @Digits(integer = 5, fraction = 0, message = "Enter a weighted annual water consumption, up to 5 digits long", groups = PreMarch2021Field.class)
  private String annualWaterConsumption;

  @FieldPrompt("Rated capacity in kg, for the standard 60°C cotton programme at full load or the standard 40°C cotton programme at full load [kg]")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity, up to 2 digits long with an optional decimal place", groups = PreMarch2021Field.class)
  private String capacity;

  @FieldPrompt("Airborne acoustical noise emissions during the washing phase for the standard 60°C cotton programme at full load expressed in dB(A) re 1pW")
  @Digits(integer = 2, fraction = 0, message = "Enter a noise emission during the washing phase, up to 2 digits long", groups = PreMarch2021Field.class)
  private String washingNoiseEmissions;

  @FieldPrompt("Airborne acoustical noise emissions during the spinning phase for the standard 60°C cotton programme at full load expressed in dB(A) re 1pW")
  @Digits(integer = 2, fraction = 0, message = "Enter a noise emission during the spinning phase, up to 2 digits long", groups = PreMarch2021Field.class)
  private String spinningNoiseEmissions;

  // Post march 2021 fields
  @FieldPrompt("Weighted energy consumption per 100 cycles in kWh, rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter a weighted energy consumption per 100 cycles, up to 3 digits long", groups = PostMarch2021Field.class)
  private String energyConsumptionPer100Cycles;

  @FieldPrompt("Rated capacity in Kg for the the eco 40-60 programme")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity for the eco 40-60 programme, up to 2 digits long with an optional decimal place", groups = PostMarch2021Field.class)
  private String ecoRatedCapacity;

  @FieldPrompt("Weighted water consumption per cycle in litres, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter a weighted water consumption per cycle, up to 2 digits long", groups = PostMarch2021Field.class)
  private String waterConsumptionPerCycle;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the eco 40-60 programme, up to 1 digit. If the programme is under 1 hour, enter 0", groups = PostMarch2021Field.class)
  private String programmeDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the eco 40-60 programme, between 0 and 59", groups = PostMarch2021Field.class)
  private String programmeDurationMinutes;

  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class", groups = PostMarch2021Field.class)
  private String noiseEmissionClass;

  @FieldPrompt("Airborne acoustic noise emission of the spinning phase in dB(A) re 1 pW, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the airborne acoustic noise emission, up to 2 digits", groups = PostMarch2021Field.class)
  private String noiseEmissionValue;

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

  public String getSpinDryingEfficiencyRating() {
    return spinDryingEfficiencyRating;
  }

  public void setSpinDryingEfficiencyRating(String spinDryingEfficiencyRating) {
    this.spinDryingEfficiencyRating = spinDryingEfficiencyRating;
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

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public String getWashingNoiseEmissions() {
    return washingNoiseEmissions;
  }

  public void setWashingNoiseEmissions(String washingNoiseEmissions) {
    this.washingNoiseEmissions = washingNoiseEmissions;
  }

  public String getSpinningNoiseEmissions() {
    return spinningNoiseEmissions;
  }

  public void setSpinningNoiseEmissions(String spinningNoiseEmissions) {
    this.spinningNoiseEmissions = spinningNoiseEmissions;
  }

  public String getEnergyConsumptionPer100Cycles() {
    return energyConsumptionPer100Cycles;
  }

  public void setEnergyConsumptionPer100Cycles(String energyConsumptionPer100Cycles) {
    this.energyConsumptionPer100Cycles = energyConsumptionPer100Cycles;
  }

  public String getEcoRatedCapacity() {
    return ecoRatedCapacity;
  }

  public void setEcoRatedCapacity(String ecoRatedCapacity) {
    this.ecoRatedCapacity = ecoRatedCapacity;
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

  public String getNoiseEmissionClass() {
    return noiseEmissionClass;
  }

  public void setNoiseEmissionClass(String noiseEmissionClass) {
    this.noiseEmissionClass = noiseEmissionClass;
  }

  public String getNoiseEmissionValue() {
    return noiseEmissionValue;
  }

  public void setNoiseEmissionValue(String noiseEmissionValue) {
    this.noiseEmissionValue = noiseEmissionValue;
  }
}
