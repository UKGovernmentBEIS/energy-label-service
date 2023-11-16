package uk.gov.beis.els.api.categories.tumbledryers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.tumbledryers.service.TumbleDryersService;

@Schema(name = "Tumble dryer arrow image")
public class TumbleDryerInternetLabelApiForm  extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = TumbleDryersService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
