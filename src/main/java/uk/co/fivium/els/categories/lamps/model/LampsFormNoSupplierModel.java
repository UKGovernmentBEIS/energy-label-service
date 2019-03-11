package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class LampsFormNoSupplierModel {

  @NotBlank
  private String efficiencyRating;

  @Digits(integer = 3, fraction = 0, message = "Must be a whole number up to 3 digits long")
  private String energyConsumption;

  @NotBlank
  private String templateType;

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

  public String getTemplateType() {
    return templateType;
  }

  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }
}
