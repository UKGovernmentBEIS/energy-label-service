package uk.gov.beis.els.api.categories.ventilationunits;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.ventilationunits.service.VentilationUnitsService;

@Schema(name = "Ventilation unit arrow image")
public class VentilationUnitsInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = VentilationUnitsService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
