package uk.co.fivium.els.categories.waterheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class HotWaterStorageTanksForm extends StandardTemplateForm50Char {

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Standing loss in W")
  @Digits(integer = 3, fraction = 0, message = "Enter the standing loss, up to 3 digits long")
  private String standingLoss;

  @FieldPrompt("Hot water storage tank volume, L")
  @Digits(integer = 3, fraction = 0, message = "Enter the tank volume, up to 3 digits long")
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
