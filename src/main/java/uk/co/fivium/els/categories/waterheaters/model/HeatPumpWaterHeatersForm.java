package uk.co.fivium.els.categories.waterheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class HeatPumpWaterHeatersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for colder climate conditions, up to 4 digits long")
  private String colderKwhAnnum;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for average climate conditions, up to 4 digits long")
  private String averageKwhAnnum;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for warmer climate conditions, up to 4 digits long")
  private String warmerKwhAnnum;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for colder climate conditions, up to 2 digits long")
  private String colderGjAnnum;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for average climate conditions, up to 2 digits long")
  private String averageGjAnnum;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for warmer climate conditions, up to 2 digits long")
  private String warmerGjAnnum;

  // TODO Create Digits variant which allows optional. Auto set (optional) in prompt
  @FieldPrompt("Sound power level, indoors dB (optional)")
  @Pattern(regexp = "[0-9]{0,2}", message = "Enter the indoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power level, outdoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelOutdoors;

  @FieldPrompt("Can the heat pump be set to work only during off-peak hours?")
  @NotNull(message = "Specify if the heat pump can be set to work only during off-peak hours")
  private Boolean canRunOffPeakOnly;

  public String getDeclaredLoadProfile() {
    return declaredLoadProfile;
  }

  public void setDeclaredLoadProfile(String declaredLoadProfile) {
    this.declaredLoadProfile = declaredLoadProfile;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getColderKwhAnnum() {
    return colderKwhAnnum;
  }

  public void setColderKwhAnnum(String colderKwhAnnum) {
    this.colderKwhAnnum = colderKwhAnnum;
  }

  public String getAverageKwhAnnum() {
    return averageKwhAnnum;
  }

  public void setAverageKwhAnnum(String averageKwhAnnum) {
    this.averageKwhAnnum = averageKwhAnnum;
  }

  public String getWarmerKwhAnnum() {
    return warmerKwhAnnum;
  }

  public void setWarmerKwhAnnum(String warmerKwhAnnum) {
    this.warmerKwhAnnum = warmerKwhAnnum;
  }

  public String getColderGjAnnum() {
    return colderGjAnnum;
  }

  public void setColderGjAnnum(String colderGjAnnum) {
    this.colderGjAnnum = colderGjAnnum;
  }

  public String getAverageGjAnnum() {
    return averageGjAnnum;
  }

  public void setAverageGjAnnum(String averageGjAnnum) {
    this.averageGjAnnum = averageGjAnnum;
  }

  public String getWarmerGjAnnum() {
    return warmerGjAnnum;
  }

  public void setWarmerGjAnnum(String warmerGjAnnum) {
    this.warmerGjAnnum = warmerGjAnnum;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public String getSoundPowerLevelOutdoors() {
    return soundPowerLevelOutdoors;
  }

  public void setSoundPowerLevelOutdoors(String soundPowerLevelOutdoors) {
    this.soundPowerLevelOutdoors = soundPowerLevelOutdoors;
  }

  public Boolean getCanRunOffPeakOnly() {
    return canRunOffPeakOnly;
  }

  public void setCanRunOffPeakOnly(Boolean canRunOffPeakOnly) {
    this.canRunOffPeakOnly = canRunOffPeakOnly;
  }

}
