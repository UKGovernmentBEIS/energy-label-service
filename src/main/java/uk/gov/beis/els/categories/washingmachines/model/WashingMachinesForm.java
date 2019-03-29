package uk.gov.beis.els.categories.washingmachines.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 110mm x 220mm when printed.")
public class WashingMachinesForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter a weighted energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Weighted annual water consumption (AWC) in litres per year, rounded to the nearest integer [L/annum]")
  @Digits(integer = 5, fraction = 0, message = "Enter a weighted annual water consumption, up to 5 digits long")
  private String annualWaterConsumption;

  @FieldPrompt("Rated capacity in kg, for the standard 60°C cotton programme at full load or the standard 40°C cotton programme at full load [kg]")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity, up to 2 digits long with an optional decimal place")
  private String capacity;

  @FieldPrompt("Spin-drying efficiency class")
  @NotBlank(message = "Select a spin-drying efficiency class")
  private String spinDryingEfficiencyRating;

  @FieldPrompt("Airborne acoustical noise emissions during the washing phase for the standard 60°C cotton programme at full load expressed in dB(A) re 1pW")
  @Digits(integer = 2, fraction = 0, message = "Enter a noise emission during the washing phase, up to 2 digits long")
  private String washingNoiseEmissions;

  @FieldPrompt("Airborne acoustical noise emissions during the spinning phase for the standard 60°C cotton programme at full load expressed in dB(A) re 1pW")
  @Digits(integer = 2, fraction = 0, message = "Enter a noise emission during the spinning phase, up to 2 digits long")
  private String spinningNoiseEmissions;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
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

  public String getSpinDryingEfficiencyRating() {
    return spinDryingEfficiencyRating;
  }

  public void setSpinDryingEfficiencyRating(String spinDryingEfficiencyRating) {
    this.spinDryingEfficiencyRating = spinDryingEfficiencyRating;
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

}
