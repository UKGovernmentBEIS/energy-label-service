package uk.gov.beis.els.api.categories.waterheaters;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;

@Schema(name = "Package of a water heater and solar device arrow image")
public class WaterSolarPackageInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = WaterHeatersService.class, legislationCategoryFieldName = "LEGISLATION_CATEGORY_SOLAR_PACKAGES")
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
