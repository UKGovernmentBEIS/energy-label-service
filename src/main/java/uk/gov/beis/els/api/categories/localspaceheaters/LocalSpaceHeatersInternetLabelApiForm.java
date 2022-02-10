package uk.gov.beis.els.api.categories.localspaceheaters;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.localspaceheaters.service.LocalSpaceHeatersService;

@Schema(name = "Local space heaters arrow image")
public class LocalSpaceHeatersInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class of the model")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = LocalSpaceHeatersService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
