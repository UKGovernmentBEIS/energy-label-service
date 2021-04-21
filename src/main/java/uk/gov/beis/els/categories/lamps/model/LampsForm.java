package uk.gov.beis.els.categories.lamps.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostSeptember2021Field;
import uk.gov.beis.els.categories.common.StandardTemplateForm20Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.lamps.model.validation.LampsFormSequenceProvider;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("<p>Old-style labels must usually be at least 36mm x 75mm when attached to packaging. You can scale down the label if no side of the packaging is large enough to contain the label, or if the label would cover more than 50% of the surface area of the largest side. You must only scale down the label enough to meet these conditions, and the label must never be less than 14.4mm x 30mm.</p><p>New-style rescaled labels must be at least 36mm x 72mm, or 20mm x 54mm for the small version of the label.</p>")
@GroupSequenceProvider(LampsFormSequenceProvider.class)
public class LampsForm extends StandardTemplateForm20Char {

  @FieldPrompt("What kind of label do you need to create?")
  @NotBlank(message = "Select the kind of label you need to create", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter an energy consumption, up to 3 digits long")
  private String energyConsumption;

  // Post September 2021 fields
  @FieldPrompt(value = "What size of label do you need to create?", hintText = "You must only use the small label on packaging less than 36mm wide")
  @NotBlank(message = "Select the size of label you need to create", groups = {PostSeptember2021Field.class})
  private String templateSize;

  @FieldPrompt(value = "Should the label be in colour or black and white?", hintText = "You must only use a black and white label if all other information on the packaging, including graphics, is printed in black and white")
  @NotBlank(message = "Select whether the label should be in colour or black and white", groups = {PostSeptember2021Field.class})
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

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

  public String getTemplateSize() {
    return templateSize;
  }

  public void setTemplateSize(String templateSize) {
    this.templateSize = templateSize;
  }

  public String getTemplateColour() {
    return templateColour;
  }

  public void setTemplateColour(String templateColour) {
    this.templateColour = templateColour;
  }
}
