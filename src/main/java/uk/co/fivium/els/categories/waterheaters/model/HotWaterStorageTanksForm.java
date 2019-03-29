package uk.co.fivium.els.categories.waterheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
public class HotWaterStorageTanksForm extends StandardTemplateForm50Char {

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Standing loss in watts (W)")
  @Digits(integer = 3, fraction = 0, message = "Enter the standing loss, up to 3 digits long")
  private String standingLoss;

  @FieldPrompt("Volume of hot water storage tank in litres (l)")
  @Digits(integer = 3, fraction = 0, message = "Enter the tank's volume, up to 3 digits long")
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
