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
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Solar water heater energy label")
@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
@GroupSequenceProvider(SolarWaterHeatersApiFormSequenceProvider.class)
public class SolarWaterHeatersApiForm extends ClimateConditionApiForm {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  @ApiValuesFromEnum(LoadProfile.class)
  private String declaredLoadProfile;

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = WaterHeatersService.class)
  private String efficiencyRating;

  @FieldPrompt("Sound power level, indoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  @NotNull
  @Schema(type = "integer")
  private String soundPowerLevelIndoors;


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
}
