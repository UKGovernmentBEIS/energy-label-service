package uk.gov.beis.els.api.categories.refrigeratordirectsales;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.RescaledInternetLabelApiForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.service.RefrigeratorsDirectSalesService;

@Schema(name = "Supermarket refrigerator, freezer cabinets or gelato-scooping cabinets arrow image")
public class DisplayCabinetsInternetLabelApiForm extends RescaledInternetLabelApiForm {

  @Schema(description = "Energy efficiency class for supermarket refrigerators, freezer cabinets or gelato-scooping cabinets")
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
