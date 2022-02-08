package uk.gov.beis.els.api.categories.dishwashers;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.RescaledInternetLabelApiForm;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;

@Schema(name = "Dishwasher internet label")
public class DishwashersInternetLabelApiForm extends RescaledInternetLabelApiForm {

  @Schema(description = "The energy efficiency class of the cavity")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = DishwashersService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
