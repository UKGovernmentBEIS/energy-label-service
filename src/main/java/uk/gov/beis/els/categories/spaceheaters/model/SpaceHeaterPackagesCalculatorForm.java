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
  private boolean hasTemperatureControl;

  @FieldPrompt("Temperature control class")
  @NotNull(message = "Select the temperature control class",
      groups = TemperatureControlGroup.class)
  private String temperatureControlClass;

  @FieldPrompt("Supplementary boiler installed?")
  @NotNull(message = "Specify if this package includes a supplementary boiler")
  private boolean hasSupplementaryBoiler;

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
  private boolean hasSolarCollector;

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
  private boolean hasStorageTank;

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
  private boolean spaceHeater;

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

  public abstract String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage();

  public abstract void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage(String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage);

  public abstract String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage();

  public abstract void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage(String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage);

  public abstract boolean isLowTemperatureHeatPump();

  public abstract void setLowTemperatureHeatPump(boolean isLowTemperatureHeatPump);

  public abstract boolean isHasSupplementaryHeatPump();

  public abstract void setHasSupplementaryHeatPump(boolean hasSupplementaryHeatPump);

  public abstract String getSupplementaryHeatPumpHeatOutput();

  public abstract void setSupplementaryHeatPumpHeatOutput(String supplementaryHeatPumpHeatOutput);

  public abstract String getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage();

  public abstract void setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage(String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage);

  public boolean isHasTemperatureControl() {
    return hasTemperatureControl;
  }

  public void setHasTemperatureControl(boolean hasTemperatureControl) {
    this.hasTemperatureControl = hasTemperatureControl;
  }

  public TemperatureControlClass getTemperatureControlClass() {
    return temperatureControlClass;
  }

  public void setTemperatureControlClass(
      TemperatureControlClass temperatureControlClass) {
    this.temperatureControlClass = temperatureControlClass;
  }

  public boolean isHasSupplementaryBoiler() {
    return hasSupplementaryBoiler;
  }

  public void setHasSupplementaryBoiler(boolean hasSupplementaryBoiler) {
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

  public boolean isHasSolarCollector() {
    return hasSolarCollector;
  }

  public void setHasSolarCollector(boolean hasSolarCollector) {
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

  public boolean isHasStorageTank() {
    return hasStorageTank;
  }

  public void setHasStorageTank(boolean hasStorageTank) {
    this.hasStorageTank = hasStorageTank;
  }

  public String getStorageTankVolume() {
    return storageTankVolume;
  }

  public void setStorageTankVolume(String storageTankVolume) {
    this.storageTankVolume = storageTankVolume;
  }

  public TankLabelClass getStorageTankRating() {
    return storageTankRating;
  }

  public void setStorageTankRating(TankLabelClass storageTankRating) {
    this.storageTankRating = storageTankRating;
  }

  public boolean isSpaceHeater() {
    return spaceHeater;
  }

  public void setSpaceHeater(boolean spaceHeater) {
    this.spaceHeater = spaceHeater;
  }
}