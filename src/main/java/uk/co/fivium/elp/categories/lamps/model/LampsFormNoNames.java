package uk.co.fivium.elp.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LampsFormNoNames {

  @NotBlank
  private String efficiencyRating;

  @NotNull
  @Digits(integer = 3, fraction = 0, message = "Must be a whole number up to 3 digits long")
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


  // setText(templateDom, "kwh", form.getEnergyConsumption());
  // "rating"

}
