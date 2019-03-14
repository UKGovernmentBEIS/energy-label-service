package uk.co.fivium.els.categories.tumbledryers.model;

import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.model.FieldPrompt;

public class CondenserTumbleDryersForm extends TumbleDryersForm {

  @FieldPrompt("Condensation efficiency class indicator")
  @NotBlank(message = "Select a condensation efficiency class")
  private String condensationEfficiencyRating;

  public String getCondensationEfficiencyRating() {
    return condensationEfficiencyRating;
  }

  public void setCondensationEfficiencyRating(String condensationEfficiencyRating) {
    this.condensationEfficiencyRating = condensationEfficiencyRating;
  }

}