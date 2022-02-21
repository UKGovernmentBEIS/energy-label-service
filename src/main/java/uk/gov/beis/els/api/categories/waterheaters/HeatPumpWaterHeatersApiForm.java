package uk.gov.beis.els.api.categories.waterheaters;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Heat pump water heater energy label")
@GroupSequenceProvider(HeatPumpWaterHeatersApiFormSequenceProvider.class)
public class HeatPumpWaterHeatersApiForm extends ClimateConditionApiForm {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  @ApiValuesFromEnum(value = LoadProfile.class)
  private String declaredLoadProfile;

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = WaterHeatersService.class)
  private String efficiencyRating;

  @FieldPrompt("Sound power level, indoors dB (optional)")
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  @Schema(type = "integer")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power level, outdoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  @NotNull
  @Schema(type = "integer")
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
