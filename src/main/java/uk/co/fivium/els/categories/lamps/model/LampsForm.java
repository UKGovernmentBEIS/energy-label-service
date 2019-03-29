package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm20Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

@StaticProductText("The label should be at least 36mm x 76mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white.")
public class LampsForm extends StandardTemplateForm20Char {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
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