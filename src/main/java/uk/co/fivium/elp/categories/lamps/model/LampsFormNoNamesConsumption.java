package uk.co.fivium.elp.categories.lamps.model;

import javax.validation.constraints.NotBlank;

public class LampsFormNoNamesConsumption {

  @NotBlank
  private String efficiencyRating;


  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  // "rating"

}
