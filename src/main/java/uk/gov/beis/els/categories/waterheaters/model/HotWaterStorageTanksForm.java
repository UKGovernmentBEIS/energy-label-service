package uk.gov.beis.els.categories.waterheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Hot water storage tank energy label")
@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
public class HotWaterStorageTanksForm extends StandardTemplateForm50Char {

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = WaterHeatersService.class)
  private String efficiencyRating;

  @FieldPrompt("Standing loss in watts (W)")
  @Digits(integer = 3, fraction = 0, message = "Enter the standing loss, up to 3 digits long")
  @NotNull
  @Schema(type = "integer")
  private String standingLoss;

  @FieldPrompt("Volume of hot water storage tank in litres (l)")
  @Digits(integer = 3, fraction = 0, message = "Enter the tank's volume, up to 3 digits long")
  @NotNull
  @Schema(type = "integer")
  private String volume;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getStandingLoss() {
    return standingLoss;
  }

  public void setStandingLoss(String standingLoss) {
    this.standingLoss = standingLoss;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }
}
