package uk.gov.beis.els.api.categories.solidfuelboilers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.solidfuelboilers.service.SolidFuelBoilersService;

@Schema(name = "Solid fuel boiler arrow image")
public class SolidFuelBoilersInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = SolidFuelBoilersService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
