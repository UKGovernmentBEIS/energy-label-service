package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.model.FieldPrompt;

public class LampsFormNoSupplierModel {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
  private String energyConsumption;

  @FieldPrompt("What type of label should be generated?")
  @NotBlank(message = "Select what type of label should be generated")
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
