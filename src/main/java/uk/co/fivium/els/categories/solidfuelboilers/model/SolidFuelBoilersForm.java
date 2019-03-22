package uk.co.fivium.els.categories.solidfuelboilers.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class SolidFuelBoilersForm extends StandardTemplateForm30Char {

  @FieldPrompt("When was the product first placed on the market?")
  @NotBlank(message = "Specify when your product was first placed on the market", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The rated heat output in kW")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output, up to 2 digits long")
  private String ratedHeatOutput;

  @FieldPrompt("Is this model a combination boiler?")
  @NotNull(message = "Specify if this model is a combination boiler")
  private Boolean combination;

  @FieldPrompt("Is this model a solid fuel cogeneration boiler?")
  @NotNull(message = "Specify if this model is a solid fuel cogeneration boiler")
  private Boolean cogeneration;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getRatedHeatOutput() {
    return ratedHeatOutput;
  }

  public void setRatedHeatOutput(String ratedHeatOutput) {
    this.ratedHeatOutput = ratedHeatOutput;
  }

  public Boolean getCombination() {
    return combination;
  }

  public void setCombination(Boolean combination) {
    this.combination = combination;
  }

  public Boolean getCogeneration() {
    return cogeneration;
  }

  public void setCogeneration(Boolean cogeneration) {
    this.cogeneration = cogeneration;
  }
}
