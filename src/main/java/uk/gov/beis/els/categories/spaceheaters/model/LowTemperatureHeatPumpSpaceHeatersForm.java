package uk.gov.beis.els.categories.spaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Low-temperature heat pump space heaters energy label")
@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
public class LowTemperatureHeatPumpSpaceHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("The seasonal space heating energy efficiency class under average climate conditions for low temperature")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = SpaceHeatersService.class)
  private String lowTempEfficiencyRating;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for colder climate conditions, up to 2 digits long")
  @Schema(
      type = "integer",
      description = "The total rated heat output for low temperature application, in colder climate conditions. In kW, up to 2 digits long"
  )
  @NotNull
  private String lowTempColderHeatOutput;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for average climate conditions, up to 2 digits long")
  @Schema(
      type = "integer",
      description = "The total rated heat output for low temperature application, in average climate conditions. In kW, up to 2 digits long"
  )
  @NotNull
  private String lowTempAverageHeatOutput;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for warmer climate conditions, up to 2 digits long")
  @Schema(
      type = "integer",
      description = "The total rated heat output for low temperature application, in warmer climate conditions. In kW, up to 2 digits long"
  )
  @NotNull
  private String lowTempWarmerHeatOutput;

  // TODO Create Digits variant which allows optional. Auto set (optional) in prompt
  @FieldPrompt("Sound power level, indoors dB (optional)")
  @Pattern(regexp = "[0-9]{0,2}", message = "Enter the indoors sound power level, up to 2 digits long")
  @Schema(
      type = "integer",
      description = "Sound power level, indoors dB (optional), up to 2 digits long"
  )
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power level, outdoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String soundPowerLevelOutdoors;

  public String getLowTempEfficiencyRating() {
    return lowTempEfficiencyRating;
  }

  public void setLowTempEfficiencyRating(String lowTempEfficiencyRating) {
    this.lowTempEfficiencyRating = lowTempEfficiencyRating;
  }

  public String getLowTempColderHeatOutput() {
    return lowTempColderHeatOutput;
  }

  public void setLowTempColderHeatOutput(String lowTempColderHeatOutput) {
    this.lowTempColderHeatOutput = lowTempColderHeatOutput;
  }

  public String getLowTempAverageHeatOutput() {
    return lowTempAverageHeatOutput;
  }

  public void setLowTempAverageHeatOutput(String lowTempAverageHeatOutput) {
    this.lowTempAverageHeatOutput = lowTempAverageHeatOutput;
  }

  public String getLowTempWarmerHeatOutput() {
    return lowTempWarmerHeatOutput;
  }

  public void setLowTempWarmerHeatOutput(String lowTempWarmerHeatOutput) {
    this.lowTempWarmerHeatOutput = lowTempWarmerHeatOutput;
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
}
