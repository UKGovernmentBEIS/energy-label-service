package uk.gov.beis.els.api.categories.washingmachines;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.RescaledInternetLabelApiForm;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;

@Schema(name = "Washing machines and washer-dryers arrow image")
public class WashingMachinesInternetLabelApiForm extends RescaledInternetLabelApiForm {

  @Schema(description = "Energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = WashingMachinesService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
