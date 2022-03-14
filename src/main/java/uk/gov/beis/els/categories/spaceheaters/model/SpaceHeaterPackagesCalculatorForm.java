package uk.gov.beis.els.categories.spaceheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.model.meta.FieldPrompt;

@GroupSequenceProvider(SpaceHeaterPackagesCalculatorFormSequenceProvider.class)
public abstract class SpaceHeaterPackagesCalculatorForm extends StandardTemplateForm50Char {

  @FieldPrompt("Rated heat output (Prated) of the preferential heater (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the heat output of the preferential heater")
  private String preferentialHeaterHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of the preferential heater (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the space heating energy efficiency of the preferential heater")
  private String preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage;

  @FieldPrompt("Temperature control installed?")
  @NotNull(message = "Specify if this package includes temperature control")
  private Boolean hasTemperatureControl;

  @FieldPrompt("Temperature control class")
  @NotNull(message = "Select the temperature control class",
      groups = TemperatureControlGroup.class)
  private String temperatureControlClass;

  @FieldPrompt("Supplementary boiler installed?")
  @NotNull(message = "Specify if this package includes a supplementary boiler")
  private Boolean hasSupplementaryBoiler;

  @FieldPrompt("Rated heat output (Prated) of the supplementary boiler (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the rated heat output of the supplementary boiler",
      groups = SupplementaryBoilerGroup.class)
  private String supplementaryBoilerHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of supplementary boiler (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of supplementary boiler",
      groups = SupplementaryBoilerGroup.class)
  private String supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage;

  @FieldPrompt("Solar collector installed?")
  @NotNull(message = "Specify if this package includes a solar collector")
  private Boolean hasSolarCollector;

  @FieldPrompt("Solar collector area (in m2)")
  @Digits(integer = 3, fraction = 0, message = "Enter the solar collector area",
      groups = SolarCollectorGroup.class)
  private String solarCollectorSize;

  @FieldPrompt("Solar collector efficiency (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the solar collector efficiency",
      groups = SolarCollectorGroup.class)
  private String solarCollectorEfficiencyPercentage;

  @FieldPrompt("Heat storage tank installed?")
  @NotNull(message = "Specify if this package includes a storage tank")
  private Boolean hasStorageTank;

  @FieldPrompt("Volume of the heat storage tank (in l)")
  @Digits(integer = 4, fraction = 0, message = "Enter the storage tank volume",
      groups = StorageTankGroup.class)
  private String storageTankVolume;

  @FieldPrompt("Tank label class")
  @NotNull(message = "Enter the storage tank label class",
      groups = StorageTankGroup.class)
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

  public Boolean getHasTemperatureControl() {
    return hasTemperatureControl;
  }

  public void setHasTemperatureControl(Boolean hasTemperatureControl) {
    this.hasTemperatureControl = hasTemperatureControl;
  }

  public String getTemperatureControlClass() {
    return temperatureControlClass;
  }

  public void setTemperatureControlClass(
      String temperatureControlClass) {
    this.temperatureControlClass = temperatureControlClass;
  }

  public Boolean getHasSupplementaryBoiler() {
    return hasSupplementaryBoiler;
  }

  public void setHasSupplementaryBoiler(Boolean hasSupplementaryBoiler) {
    this.hasSupplementaryBoiler = hasSupplementaryBoiler;
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

  public Boolean getHasSolarCollector() {
    return hasSolarCollector;
  }

  public void setHasSolarCollector(Boolean hasSolarCollector) {
    this.hasSolarCollector = hasSolarCollector;
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

  public Boolean getHasStorageTank() {
    return hasStorageTank;
  }

  public void setHasStorageTank(Boolean hasStorageTank) {
    this.hasStorageTank = hasStorageTank;
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