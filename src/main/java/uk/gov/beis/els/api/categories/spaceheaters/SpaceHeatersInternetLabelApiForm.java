package uk.gov.beis.els.api.categories.spaceheaters;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;

@Schema(name = "Space heaters arrow image")
public class SpaceHeatersInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The seasonal space heating energy efficiency class")
  @ApiValuesFromLegislationCategory(serviceClass = SpaceHeatersService.class)
  @NotNull
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
