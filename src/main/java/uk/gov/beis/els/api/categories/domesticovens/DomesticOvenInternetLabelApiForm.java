package uk.gov.beis.els.api.categories.domesticovens;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.domesticovens.service.DomesticOvensService;

@Schema(name = "Domestic oven arrow image")
public class DomesticOvenInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class of the cavity")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = DomesticOvensService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
