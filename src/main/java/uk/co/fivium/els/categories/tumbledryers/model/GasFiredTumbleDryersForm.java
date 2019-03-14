package uk.co.fivium.els.categories.tumbledryers.model;

import uk.co.fivium.els.model.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class GasFiredTumbleDryersForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark") // TODO max length?
  private String supplierName;

  @FieldPrompt("Supplier's model identifier")
  @NotBlank(message = "Enter a supplier model identifier")
  private String modelName;

  @FieldPrompt("Energy efficiency class of the tumble dryer")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("Weighted annual energy consumption (AEC) in kWh per year, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
  private String energyConsumption;

  @FieldPrompt("Cycle time corresponding to the standard cotton programme at full load in minutes per cycle")
  @Digits(integer = 3, fraction = 0, message = "Enter the cycle time in minutes, up to 3 digits long")
  private String cycleTime;

  @FieldPrompt("Rated capacity, for the standard cotton programme at full load in kg")
  @Digits(integer = 1, fraction = 1, message = "Enter the capacity, rounded to the nearest 0.1 kg")
  private String ratedCapacity;

  @FieldPrompt("Sound power level (weighted average value — LWA), during the drying phase, for the standard cotton programme at full load, expressed in dB ")
  @Digits(integer = 2, fraction = 0, message = "Enter the sound power level, up to 2 digits long")
  private String soundPowerLevel;

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

  public String getEnergyConsumption() {
    return energyConsumption;
  }

  public void setEnergyConsumption(String energyConsumption) {
    this.energyConsumption = energyConsumption;
  }


  public String getCycleTime() {
    return cycleTime;
  }

  public void setCycleTime(String cycleTime) {
    this.cycleTime = cycleTime;
  }

  public String getRatedCapacity() {
    return ratedCapacity;
  }

  public void setRatedCapacity(String ratedCapacity) {
    this.ratedCapacity = ratedCapacity;
  }

  public String getSoundPowerLevel() {
    return soundPowerLevel;
  }

  public void setSoundPowerLevel(String soundPowerLevel) {
    this.soundPowerLevel = soundPowerLevel;
  }

}