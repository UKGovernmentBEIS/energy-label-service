package uk.co.fivium.els.categories.spaceheaters.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

public class CombinationHeaterPackagesForm extends StandardTemplateForm50Char {

  @FieldPrompt("The seasonal space heating energy efficiency class of the space heater")
  @NotBlank(message = "Select the space heating energy efficiency class of the space heater")
  private String spaceHeaterEfficiencyRating;

  @FieldPrompt("Water heating energy efficiency")
  @NotBlank(message = "Select the water heating energy efficiency")
  private String waterHeaterEfficiencyRating;

  @FieldPrompt("Declared load profile of the space heater")
  @NotBlank(message = "Select a declared load profile")
  private String heaterDeclaredLoadProfile;

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
  @NotBlank(message = "Select a space heating energy efficiency indicator for the complete package", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String packageSpaceHeatingEfficiencyRating;

  @FieldPrompt("The water heating energy efficiency class of the package of combination heater, temperature control and solar device")
  @NotBlank(message = "Select a water heating energy efficiency indicator for the complete package")
  private String packageWaterHeatingEfficiencyRating;

  @FieldPrompt("Declared load profile of the package")
  @NotBlank(message = "Select a declared load profile")
  private String packageDeclaredLoadProfile;

  public String getSpaceHeaterEfficiencyRating() {
    return spaceHeaterEfficiencyRating;
  }

  public void setSpaceHeaterEfficiencyRating(String spaceHeaterEfficiencyRating) {
    this.spaceHeaterEfficiencyRating = spaceHeaterEfficiencyRating;
  }

  public String getWaterHeaterEfficiencyRating() {
    return waterHeaterEfficiencyRating;
  }

  public void setWaterHeaterEfficiencyRating(String waterHeaterEfficiencyRating) {
    this.waterHeaterEfficiencyRating = waterHeaterEfficiencyRating;
  }

  public String getHeaterDeclaredLoadProfile() {
    return heaterDeclaredLoadProfile;
  }

  public void setHeaterDeclaredLoadProfile(String heaterDeclaredLoadProfile) {
    this.heaterDeclaredLoadProfile = heaterDeclaredLoadProfile;
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

  public String getPackageSpaceHeatingEfficiencyRating() {
    return packageSpaceHeatingEfficiencyRating;
  }

  public void setPackageSpaceHeatingEfficiencyRating(String packageSpaceHeatingEfficiencyRating) {
    this.packageSpaceHeatingEfficiencyRating = packageSpaceHeatingEfficiencyRating;
  }

  public String getPackageWaterHeatingEfficiencyRating() {
    return packageWaterHeatingEfficiencyRating;
  }

  public void setPackageWaterHeatingEfficiencyRating(String packageWaterHeatingEfficiencyRating) {
    this.packageWaterHeatingEfficiencyRating = packageWaterHeatingEfficiencyRating;
  }

  public String getPackageDeclaredLoadProfile() {
    return packageDeclaredLoadProfile;
  }

  public void setPackageDeclaredLoadProfile(String packageDeclaredLoadProfile) {
    this.packageDeclaredLoadProfile = packageDeclaredLoadProfile;
  }
}
