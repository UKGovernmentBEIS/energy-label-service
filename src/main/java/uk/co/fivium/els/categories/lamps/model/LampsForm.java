package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.categories.common.StandardTemplateForm20Char;
import uk.co.fivium.els.model.FieldPrompt;

public class LampsForm extends StandardTemplateForm20Char {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
  private String energyConsumption;

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