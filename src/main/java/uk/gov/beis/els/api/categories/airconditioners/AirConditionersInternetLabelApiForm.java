package uk.gov.beis.els.api.categories.airconditioners;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.airconditioners.service.AirConditionersService;

@Schema(name = "Air conditioner arrow image")
public class AirConditionersInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The energy efficiency class for cooling")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = AirConditionersService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
