package uk.gov.beis.els.categories.tumbledryers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.tumbledryers.service.TumbleDryersService;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Condenser tumble dryers energy label")
public class CondenserTumbleDryersForm extends TumbleDryersForm {

  @FieldPrompt("Condensation efficiency class indicator")
  @NotBlank(message = "Select a condensation efficiency class")
  @ApiValuesFromLegislationCategory(serviceClass = TumbleDryersService.class)
  private String condensationEfficiencyRating;

  public String getCondensationEfficiencyRating() {
    return condensationEfficiencyRating;
  }

  public void setCondensationEfficiencyRating(String condensationEfficiencyRating) {
    this.condensationEfficiencyRating = condensationEfficiencyRating;
  }

}