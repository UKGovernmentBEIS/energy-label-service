package uk.co.fivium.elp.categories.lamps.model;

import javax.validation.constraints.NotBlank;

public class LampsFormNoSupplierModelConsumption {

  @NotBlank
  private String efficiencyRating;

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
