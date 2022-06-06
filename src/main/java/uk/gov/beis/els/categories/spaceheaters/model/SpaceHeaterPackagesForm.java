package uk.gov.beis.els.categories.spaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.spaceheaters.service.SpaceHeatersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Schema(name = "Packages of space heater, temperature control and solar device energy label")
@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 210mm x 297mm when printed.")
public class SpaceHeaterPackagesForm extends StandardTemplateForm50Char {

  @FieldPrompt("The seasonal space heating energy efficiency class of the space heater")
  @NotBlank(message = "Select an energy efficiency indicator for the space heater")
  @ApiValuesFromLegislationCategory(
      serviceClass = SpaceHeatersService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_PACKAGES"
  )
  private String heaterEfficiencyRating;

  @FieldPrompt("Does this package include a solar collector?")
  @NotNull(message = "Specify if this package includes a solar collector")
  private Boolean solarCollector;

  @FieldPrompt("Does this package include a hot water storage tank?")
  @NotNull(message = "Specify if this package includes a hot water storage tank")
  private Boolean hotWaterStorageTank;

  @FieldPrompt("Does this package include a temperature control?")
  @NotNull(message = "Specify if this package includes a temperature control")
  private Boolean temperatureControl;

  @FieldPrompt("Does this package include a supplementary space heater?")
  @NotNull(message = "Specify if this package includes a supplementary space heater")
  private Boolean spaceHeater;

  @FieldPrompt("The seasonal space heating energy efficiency class of the package of space heater, temperature control and solar device")
  @NotBlank(message = "Select an energy efficiency indicator for the complete package", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(
      serviceClass = SpaceHeatersService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_PACKAGES"
  )
  private String packageEfficiencyRating;

  public String getHeaterEfficiencyRating() {
    return heaterEfficiencyRating;
  }

  public void setHeaterEfficiencyRating(String heaterEfficiencyRating) {
    this.heaterEfficiencyRating = heaterEfficiencyRating;
  }

  public Boolean getSolarCollector() {
    return solarCollector;
  }

  public void setSolarCollector(Boolean solarCollector) {
    this.solarCollector = solarCollector;
  }

  public Boolean getHotWaterStorageTank() {
    return hotWaterStorageTank;
  }

  public void setHotWaterStorageTank(Boolean hotWaterStorageTank) {
    this.hotWaterStorageTank = hotWaterStorageTank;
  }

  public Boolean getTemperatureControl() {
    return temperatureControl;
  }

  public void setTemperatureControl(Boolean temperatureControl) {
    this.temperatureControl = temperatureControl;
  }

  public Boolean getSpaceHeater() {
    return spaceHeater;
  }

  public void setSpaceHeater(Boolean spaceHeater) {
    this.spaceHeater = spaceHeater;
  }

  public String getPackageEfficiencyRating() {
    return packageEfficiencyRating;
  }

  public void setPackageEfficiencyRating(String packageEfficiencyRating) {
    this.packageEfficiencyRating = packageEfficiencyRating;
  }
}
