package uk.co.fivium.els.categories.spaceheaters.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

public class SpaceHeaterPackagesForm extends StandardTemplateForm50Char {

  @FieldPrompt("The seasonal space heating energy efficiency class of the space heater")
  @NotBlank(message = "Select an energy efficiency indicator for the space heater")
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

  @FieldPrompt("The seasonal space heating energy efficiency class of the package of combination heater, temperature control and solar device")
  @NotBlank(message = "Select an energy efficiency indicator for the complete package", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
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
