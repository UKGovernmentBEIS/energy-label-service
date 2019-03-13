package uk.co.fivium.els.categories.ventilationunits.model;

import uk.co.fivium.els.model.FieldPrompt;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class VentilationUnitsForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark") // TODO max length?
  private String supplierName;

  @FieldPrompt("Supplier's model identifier")
  @NotBlank(message = "Enter a supplier model identifier")
  private String modelName;

  @FieldPrompt("Energy efficiency indicator for an 'average' climate")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("Sound power level (LWA) in dB rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the sound power level, up to 2 digits long")
  private String soundPowerLevel;

  @FieldPrompt("Maximum flow rate in m3/h rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter the maximum flow rate, up to 3 digits long")
  private String maxFlowRate;

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

  public String getSoundPowerLevel() {
    return soundPowerLevel;
  }

  public void setSoundPowerLevel(String soundPowerLevel) {
    this.soundPowerLevel = soundPowerLevel;
  }

  public String getMaxFlowRate() {
    return maxFlowRate;
  }

  public void setMaxFlowRate(String maxFlowRate) {
    this.maxFlowRate = maxFlowRate;
  }
}
