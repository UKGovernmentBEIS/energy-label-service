package uk.gov.beis.els.api.categories.lamps;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.lamps.service.LampsService;

@Schema(name = "Lamps and light sources old style arrow image")
public class LampsOldStyleInternetLabelApiForm extends BaseInternetLabelApiForm {

  @NotBlank(message = "Select an energy efficiency class")
  @Schema(description = "Energy efficiency class of the application")
  @ApiValuesFromLegislationCategory(
      serviceClass = LampsService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021"
  )
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
