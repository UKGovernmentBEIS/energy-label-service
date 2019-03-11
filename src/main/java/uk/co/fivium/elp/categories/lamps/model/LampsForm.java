package uk.co.fivium.elp.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class LampsForm {

  @NotBlank // TODO max length?
  private String supplierName;

  @NotBlank
  private String modelName;

  @NotBlank
  private String efficiencyRating;

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



  // setText(templateDom, "supplier", form.getSupplierName());
  // setText(templateDom, "model", form.getModelName());
  // setText(templateDom, "kwh", form.getEnergyConsumption());
  // "rating" - A++ to E
}