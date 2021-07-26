package uk.gov.beis.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("The label should be at least 36mm x 68mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white.")
public class LampsFormNoSupplierModel extends InternetLabellingForm {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer")
  @Digits(integer = 4, fraction = 0, message = "Enter an energy consumption, up to 4 digits long")
  private String energyConsumption;

  @FieldPrompt("Should the label be in colour or black and white?")
  @NotBlank(message = "Select whether the label should be in colour or black and white")
  private String templateColour;

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

  public String getTemplateColour() {
    return templateColour;
  }

  public void setTemplateColour(String templateColour) {
    this.templateColour = templateColour;
  }
}
