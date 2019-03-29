package uk.gov.beis.els.categories.ventilationunits.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class VentilationUnitsForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency indicator for an 'average' climate")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Sound power level (LWA) in dB rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the sound power level, up to 2 digits long")
  private String soundPowerLevel;

  @FieldPrompt("Maximum flow rate in m3/h rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter the maximum flow rate, up to 3 digits long")
  private String maxFlowRate;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getSoundPowerLevel() {
    return soundPowerLevel;
  }

  public void setSoundPowerLevel(String soundPowerLevel) {
    this.soundPowerLevel = soundPowerLevel;
  }

  public String getMaxFlowRate() {
    return maxFlowRate;
  }

  public void setMaxFlowRate(String maxFlowRate) {
    this.maxFlowRate = maxFlowRate;
  }
}
