package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.model.FieldPrompt;

public class LampsFormNoSupplierModelConsumption {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank
  private String efficiencyRating;

  @FieldPrompt("What type of label should be generated?")
  @NotBlank
  private String templateType;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getTemplateType() {
    return templateType;
  }

  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }
}
