package uk.co.fivium.els.categories.solidfuelboilers.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SolidFuelBoilerPackagesForm extends StandardTemplateForm50Char {

  @FieldPrompt("Energy efficiency class of the solid fuel boiler")
  @NotBlank(message = "Select an energy efficiency indicator for the boiler")
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
  @NotBlank(message = "Select an energy efficiency indicator for the complete package")
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
