package uk.co.fivium.els.categories.washingmachines.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class WashingMachinesForm {

  @NotBlank // TODO max length?
  private String supplierName;

  @NotBlank
  private String modelName;

  @NotBlank
  private String efficiencyRating;

  @Digits(integer = 3, fraction = 0, message = "Must be a whole number up to 3 digits long")
  private String annualEnergyConsumption;

  @Digits(integer = 5, fraction = 0, message = "Must be a whole number up to 5 digits long")
  private String annualWaterConsumption;

  @Digits(integer = 2, fraction = 1) // TODO message
  private String capacity;

  @NotBlank
  private String spinDryingEfficiencyRating;

  @Digits(integer = 3, fraction = 0, message = "Must be a whole number up to 2 digits long")
  private String washingNoiseEmissions;

  @Digits(integer = 3, fraction = 0, message = "Must be a whole number up to 2 digits long")
  private String spinningNoiseEmissions;

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

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
