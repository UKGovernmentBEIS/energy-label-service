package uk.gov.beis.els.api.categories.refrigeratingappliances;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.RescaledInternetLabelApiForm;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;

@Schema(name = "Household fridges and freezers arrow image")
public class FridgesFreezersInternetLapelApiForm extends RescaledInternetLabelApiForm {

  @Schema(description = "Energy efficiency class indicator")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = RefrigeratingAppliancesService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
