package uk.co.fivium.els.categories.waterheaters.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ConventionalWaterHeatersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator")
  private String efficiencyRating;

  @FieldPrompt("Annual electricity consumption in kWh/annum")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption, up to 4 digits long")
  private String kwhAnnum;

  @FieldPrompt("Annual fuel consumption in GJ/annum")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption, up to 2 digits long")
  private String gjAnnum;

  @FieldPrompt("Sound power level, indoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Can the heater be set to work only during off-peak hours?")
  @NotNull(message = "Specify if off-peak operation is supported")
  private Boolean offPeak;

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

  public String getKwhAnnum() {
    return kwhAnnum;
  }

  public void setKwhAnnum(String kwhAnnum) {
    this.kwhAnnum = kwhAnnum;
  }

  public String getGjAnnum() {
    return gjAnnum;
  }

  public void setGjAnnum(String gjAnnum) {
    this.gjAnnum = gjAnnum;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public Boolean getOffPeak() {
    return offPeak;
  }

  public void setOffPeak(Boolean offPeak) {
    this.offPeak = offPeak;
  }
}
