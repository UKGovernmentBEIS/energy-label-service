package uk.gov.beis.els.api.categories.prorefrigeratedcabinets;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.service.ProRefrigeratedCabinetsService;

@Schema(name = "Professional refrigerated storage cabinets arrow image")
public class ProRefrigeratedCabinetsInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "Energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = ProRefrigeratedCabinetsService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
