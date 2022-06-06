package uk.gov.beis.els.categories.solidfuelboilers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.solidfuelboilers.service.SolidFuelBoilersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Package of a solid fuel boiler, supplementary heaters, temperature controls and solar devices energy label")
@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 210mm x 297mm when printed.")
public class SolidFuelBoilerPackagesForm extends StandardTemplateForm50Char {

  @FieldPrompt("Energy efficiency class of the solid fuel boiler")
  @NotBlank(message = "Select an energy efficiency indicator for the boiler")
  @ApiValuesFromLegislationCategory(serviceClass = SolidFuelBoilersService.class, legislationCategoryFieldName = "LEGISLATION_CATEGORY_PACKAGES_CURRENT")
  private String boilerEfficiencyRating;

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

  @FieldPrompt("Energy efficiency class of the package of a solid fuel boiler, supplementary heaters, temperature controls and solar devices")
  @NotBlank(message = "Select an energy efficiency indicator for the complete package", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = SolidFuelBoilersService.class, legislationCategoryFieldName = "LEGISLATION_CATEGORY_PACKAGES_CURRENT")
  private String packageEfficiencyRating;

  public String getBoilerEfficiencyRating() {
    return boilerEfficiencyRating;
  }

  public void setBoilerEfficiencyRating(String boilerEfficiencyRating) {
    this.boilerEfficiencyRating = boilerEfficiencyRating;
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
