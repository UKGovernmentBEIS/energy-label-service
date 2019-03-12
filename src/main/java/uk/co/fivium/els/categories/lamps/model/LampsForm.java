package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.model.FieldPrompt;

public class LampsForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank // TODO max length?
  private String supplierName;

  @FieldPrompt("Supplier's model identifier")
  @NotBlank
  private String modelName;

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Must be a whole number up to 3 digits long")
  private String energyConsumption;

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

}