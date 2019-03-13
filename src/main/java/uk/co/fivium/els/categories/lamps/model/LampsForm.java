package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import uk.co.fivium.els.model.FieldPrompt;

public class LampsForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark")
  @Length(max = 20, message = "Supplier name or trade mark must be 20 characters or less")
  private String supplierName;

  @FieldPrompt("Supplier's model identifier")
  @NotBlank(message = "Enter a supplier model identifier")
  @Length(max = 20, message = "Supplier model identifier must be 20 characters or less")
  private String modelName;

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
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