package uk.gov.beis.els.categories.spaceheaters.model;

import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class SpaceHeaterPackagesCalculatorForm extends StandardTemplateForm50Char {

  //TODO check if I'll need this
  private PreferentialHeaterTypes preferentialHeaterType;

  @FieldPrompt("Rated heat output (Prated) of the preferential heater type (in kW)") //TODO dynamically set this in the template
  private String preferentialHeaterHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of the preferential heater type (in %") //TODO do this dynamically
  private String preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage;

  //if preferential heater type == heatPump
  @FieldPrompt("Seasonal space heating energy efficiency of heat pump in colder climate (in %)")
  private String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;

  //if preferential heater type == heatPump
  @FieldPrompt("Seasonal space heating energy efficiency of heat pump in warmer climate (in %)")
  private String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;

  //if preferential heater type == heatPump
  @FieldPrompt("Is this a low temperature heat pump?")
  private boolean isLowTemperatureHeatPump;

  //if preferential heater type == boiler
  @FieldPrompt("Supplementary heat pump installed?")
  private boolean hasSupplementaryHeatPump;

  //if preferential heater type == boiler && hasSupplementaryHeatPump == true
  @FieldPrompt("Rated heat output (Prated) of the supplementary heat pump (in kW")
  private String supplementaryHeatPumpHeatOutput;

  //if preferential heater type == boiler && hasSupplementaryHeatPump == true
  @FieldPrompt("Seasonal space heating energy efficiency of supplementary heat pump (in %)")
  private String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;

  @FieldPrompt("Temperature control installed?")
  private boolean hasTemperatureControl;

  //if hasTemperatureControl == true
  @FieldPrompt("Temperature control class")
  private TemperatureControlClass temperatureControlClass;

  @FieldPrompt("Supplementary boiler installed?")
  private boolean hasSupplementaryBoiler;

  //if hasSupplementaryBoiler == true
  @FieldPrompt("Rated heat output (Prated) of the supplementary boiler (in kW)")
  private String supplementaryBoilerHeatOutput;

  //if hasSupplementaryBoiler == true
  @FieldPrompt("Seasonal space heating energy efficiency of supplementary boiler (in %)")
  private String supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage;

  @FieldPrompt("Solar collector installed?")
  private boolean hasSolarCollector;

  //if hasSolarCollector == true
  @FieldPrompt("Solar collector area (in m2)")
  private String solarCollectorSize;

  //if hasSolarCollector == true
  @FieldPrompt("Solar collector efficiency (in %)")
  private String solarCollectorEfficiencyPercentage;

  @FieldPrompt("Heat storage tank installed?")
  private boolean hasStorageTank;

  //if hasStorageTank == true
  @FieldPrompt("Volume of the heat storage tank (in l)")
  private String storageTankVolume;

  //if hasStorageTank == true
  @FieldPrompt("Tank label class")
  private TankLabelClass storageTankRating;

  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
  }

  public void setPreferentialHeaterType(
      PreferentialHeaterTypes preferentialHeaterType) {
    this.preferentialHeaterType = preferentialHeaterType;
  }

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

  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage() {
    return preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;
  }

  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage) {
    this.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage = preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;
  }

  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage() {
    return preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;
  }

  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage) {
    this.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage = preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;
  }

  public boolean isLowTemperatureHeatPump() {
    return isLowTemperatureHeatPump;
  }

  public void setLowTemperatureHeatPump(boolean lowTemperatureHeatPump) {
    isLowTemperatureHeatPump = lowTemperatureHeatPump;
  }

  public boolean isHasSupplementaryHeatPump() {
    return hasSupplementaryHeatPump;
  }

  public void setHasSupplementaryHeatPump(boolean hasSupplementaryHeatPump) {
    this.hasSupplementaryHeatPump = hasSupplementaryHeatPump;
  }

  public String getSupplementaryHeatPumpHeatOutput() {
    return supplementaryHeatPumpHeatOutput;
  }

  public void setSupplementaryHeatPumpHeatOutput(String supplementaryHeatPumpHeatOutput) {
    this.supplementaryHeatPumpHeatOutput = supplementaryHeatPumpHeatOutput;
  }

  public String getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage() {
    return supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;
  }

  public void setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage(
      String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage) {
    this.supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage = supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;
  }

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
}