package uk.gov.beis.els.api.categories.rangehoods;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.rangehoods.service.RangeHoodsService;

@Schema(name = "Range hood internet label")
public class RangeHoodsInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "Energy efficiency class indicator")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = RangeHoodsService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
