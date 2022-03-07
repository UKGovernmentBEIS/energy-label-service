package uk.gov.beis.els.categories.spaceheaters.model;

public class CogenerationPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.COGENERATION_HEATER;

  @Override
  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
  }

  @Override
  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage() {
    return null;
  }

  @Override
  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage) {

  }

  @Override
  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage() {
    return null;
  }

  @Override
  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage) {

  }

  @Override
  public boolean isLowTemperatureHeatPump() {
    return false;
  }

  @Override
  public void setLowTemperatureHeatPump(boolean isLowTemperatureHeatPump) {

  }

  @Override
  public boolean isHasSupplementaryHeatPump() {
    return false;
  }

  @Override
  public void setHasSupplementaryHeatPump(boolean hasSupplementaryHeatPump) {

  }

  @Override
  public String getSupplementaryHeatPumpHeatOutput() {
    return null;
  }

  @Override
  public void setSupplementaryHeatPumpHeatOutput(String supplementaryHeatPumpHeatOutput) {

  }

  @Override
  public String getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage() {
    return null;
  }

  @Override
  public void setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage(
      String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage) {

  }
}
