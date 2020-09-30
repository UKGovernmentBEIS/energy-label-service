package uk.gov.beis.els.categories.spaceheaters.model;

import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
public class CogenerationSpaceHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("The seasonal space heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The rated heat output in kW")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output, up to 2 digits long")
  private String heatOutput;

  @FieldPrompt("Sound power level, indoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Is there an additional electricity generation function?")
  @NotNull(message = "Select whether there is an additional electricity generation function")
  private Boolean electricityGeneration;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getHeatOutput() {
    return heatOutput;
  }

  public void setHeatOutput(String heatOutput) {
    this.heatOutput = heatOutput;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public Boolean getElectricityGeneration() {
    return electricityGeneration;
  }

  public void setElectricityGeneration(Boolean electricityGeneration) {
    this.electricityGeneration = electricityGeneration;
  }
}
