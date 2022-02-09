package uk.gov.beis.els.api.categories.refrigeratordirectsales;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.RescaledInternetLabelApiForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.service.RefrigeratorsDirectSalesService;

@Schema(name = "Beverage coolers arrow image")
public class BeverageCoolersInternetLabelApiForm extends RescaledInternetLabelApiForm {

  @Schema(description = "Energy efficiency class indicator")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = RefrigeratorsDirectSalesService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
