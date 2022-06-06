package uk.gov.beis.els.api.categories.lamps;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.lamps.service.LampsService;

@Schema(name = "Lamps and light sources old style energy label")
public class LampsPreSeptember2021ApiForm extends StandardTemplateForm50Char {

  @NotBlank(message = "Select an energy efficiency class")
  @Schema(description = "Energy efficiency class of the application")
  @ApiValuesFromLegislationCategory(
      serviceClass = LampsService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021"
  )
  private String efficiencyRating;

  @Digits(integer = 4, fraction = 0, message = "Enter an energy consumption, up to 4 digits long")
  @Schema(
      type = "integer",
      description = "Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer"
  )
  @NotNull
  private String energyConsumption;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getEnergyConsumption() {
    return energyConsumption;
  }

  public void setEnergyConsumption(String energyConsumption) {
    this.energyConsumption = energyConsumption;
  }
}
