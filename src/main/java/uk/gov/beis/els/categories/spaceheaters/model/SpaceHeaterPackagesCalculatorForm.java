package uk.gov.beis.els.categories.spaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.model.meta.FieldPrompt;

@GroupSequenceProvider(SpaceHeaterPackagesCalculatorFormSequenceProvider.class)
public abstract class SpaceHeaterPackagesCalculatorForm extends StandardTemplateForm50Char {

  @FieldPrompt("Rated heat output (Prated) of the preferential heater (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the heat output of the preferential heater, up to 4 digits long")
  @Schema(type = "integer")
  @NotNull
  private String preferentialHeaterHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of the preferential heater (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the space heating energy efficiency of the preferential heater, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage;

  @FieldPrompt("Does this package include a temperature control?")
  @NotNull(message = "Specify if this package includes temperature control")
  private Boolean temperatureControl;

  @FieldPrompt("Temperature control class")
  @ApiValuesFromEnum(value = TemperatureControlClass.class)
  @NotNull(message = "Select the temperature control class",
      groups = TemperatureControlGroup.class)
  @Schema(description = "Temperature control class. Only required if <code>temperatureControl</code> is <code>true</code>.")
  private String temperatureControlClass;

  @FieldPrompt("Does this package include a supplementary boiler?")
  @NotNull(message = "Specify if this package includes a supplementary boiler")
  private Boolean supplementaryBoiler;

  @FieldPrompt("Rated heat output (Prated) of the supplementary boiler (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the rated heat output of the supplementary boiler, up to 4 digits long",
      groups = SupplementaryBoilerGroup.class)
  @Schema(type = "integer", description = "Rated heat output (Prated) of the supplementary boiler (in kW). Only required if <code>supplementaryBoiler</code> is <code>true</code>.")
  @NotNull(groups = SupplementaryBoilerGroup.class, message = "Enter the rated heat output of the supplementary boiler")
  private String supplementaryBoilerHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of supplementary boiler (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of supplementary boiler, up to 3 digits long",
      groups = SupplementaryBoilerGroup.class)
  @Schema(type = "integer", description = "Seasonal space heating energy efficiency of supplementary boiler (in %). Only required if <code>supplementaryBoiler</code> is <code>true</code>.")
  @NotNull(groups = SupplementaryBoilerGroup.class, message = "Enter the seasonal space heating energy efficiency of supplementary boiler")
  private String supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage;

  @FieldPrompt("Solar collector area (in m2)")
  @Digits(integer = 3, fraction = 0, message = "Enter the solar collector area, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull(message = "Enter the solar collector area")
  private String solarCollectorSize;

  @FieldPrompt("Solar collector efficiency (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the solar collector efficiency, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull(message = "Enter the solar collector efficiency")
  private String solarCollectorEfficiencyPercentage;

  @FieldPrompt("Does this package include a hot water storage tank?")
  @NotNull(message = "Specify if this package includes a hot water storage tank")
  private Boolean storageTank;

  @FieldPrompt("Volume of the heat storage tank (in l)")
  @Digits(integer = 4, fraction = 0, message = "Enter the storage tank volume, up to 4 digits long",
      groups = StorageTankGroup.class)
  @Schema(type = "integer", description = "Volume of the heat storage tank (in l). Only required if <code>storageTank</code> is <code>true</code>.")
  @NotNull(groups = StorageTankGroup.class, message = "Enter the storage tank volume")
  private String storageTankVolume;

  @FieldPrompt("Tank label class")
  @NotNull(message = "Enter the storage tank label class",
      groups = StorageTankGroup.class)
  @ApiValuesFromEnum(TankLabelClass.class)
  @Schema(description = "Tank label class. Only required if <code>storageTankRating</code> is <code>true</code>.")
  private String storageTankRating;

  @FieldPrompt("Does this package include a supplementary space heater?")
  @NotNull(message = "Specify if the package includes a supplementary space heater")
  private Boolean spaceHeater;

  public abstract PreferentialHeaterTypes getPreferentialHeaterType();

  public String getPreferentialHeaterHeatOutput() {
    return preferentialHeaterHeatOutput;
  }

  public void setPreferentialHeaterHeatOutput(String preferentialHeaterHeatOutput) {
    this.preferentialHeaterHeatOutput = preferentialHeaterHeatOutput;
  }

  public String getPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage() {
    return preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage;
  }

  public void setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage(
      String preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage) {
    this.preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage = preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage;
  }

  public Boolean getTemperatureControl() {
    return temperatureControl;
  }

  public void setTemperatureControl(Boolean temperatureControl) {
    this.temperatureControl = temperatureControl;
  }

  public String getTemperatureControlClass() {
    return temperatureControlClass;
  }

  public void setTemperatureControlClass(
      String temperatureControlClass) {
    this.temperatureControlClass = temperatureControlClass;
  }

  public Boolean getSupplementaryBoiler() {
    return supplementaryBoiler;
  }

  public void setSupplementaryBoiler(Boolean supplementaryBoiler) {
    this.supplementaryBoiler = supplementaryBoiler;
  }

  public String getSupplementaryBoilerHeatOutput() {
    return supplementaryBoilerHeatOutput;
  }

  public void setSupplementaryBoilerHeatOutput(String supplementaryBoilerHeatOutput) {
    this.supplementaryBoilerHeatOutput = supplementaryBoilerHeatOutput;
  }

  public String getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage() {
    return supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage;
  }

  public void setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage(
      String supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage) {
    this.supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage = supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage;
  }

  public String getSolarCollectorSize() {
    return solarCollectorSize;
  }

  public void setSolarCollectorSize(String solarCollectorSize) {
    this.solarCollectorSize = solarCollectorSize;
  }

  public String getSolarCollectorEfficiencyPercentage() {
    return solarCollectorEfficiencyPercentage;
  }

  public void setSolarCollectorEfficiencyPercentage(String solarCollectorEfficiencyPercentage) {
    this.solarCollectorEfficiencyPercentage = solarCollectorEfficiencyPercentage;
  }

  public Boolean getStorageTank() {
    return storageTank;
  }

  public void setStorageTank(Boolean storageTank) {
    this.storageTank = storageTank;
  }

  public String getStorageTankVolume() {
    return storageTankVolume;
  }

  public void setStorageTankVolume(String storageTankVolume) {
    this.storageTankVolume = storageTankVolume;
  }

  public String getStorageTankRating() {
    return storageTankRating;
  }

  public void setStorageTankRating(String storageTankRating) {
    this.storageTankRating = storageTankRating;
  }

  public Boolean getSpaceHeater() {
    return spaceHeater;
  }

  public void setSpaceHeater(Boolean spaceHeater) {
    this.spaceHeater = spaceHeater;
  }
}