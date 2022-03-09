package uk.gov.beis.els.categories.spaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Packages of space heater, temperature control and solar device energy label calculator - Cogeneration heater")
public class CogenerationPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  @Schema(hidden = true)
  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.COGENERATION_HEATER;

  @Override
  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
  }

  @Override
  @Schema(hidden = true)
  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage() {
    return null;
  }

  @Override
  @Schema(hidden = true)
  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage) {

  }

  @Override
  @Schema(hidden = true)
  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage() {
    return null;
  }

  @Override
  @Schema(hidden = true)
  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage) {

  }

  @Override
  @Schema(hidden = true)
  public boolean isLowTemperatureHeatPump() {
    return false;
  }

  @Override
  @Schema(hidden = true)
  public void setLowTemperatureHeatPump(boolean isLowTemperatureHeatPump) {

  }

  @Override
  @Schema(hidden = true)
  public boolean isHasSupplementaryHeatPump() {
    return false;
  }

  @Override
  @Schema(hidden = true)
  public void setHasSupplementaryHeatPump(boolean hasSupplementaryHeatPump) {

  }

  @Override
  @Schema(hidden = true)
  public String getSupplementaryHeatPumpHeatOutput() {
    return null;
  }

  @Override
  @Schema(hidden = true)
  public void setSupplementaryHeatPumpHeatOutput(String supplementaryHeatPumpHeatOutput) {

  }

  @Override
  @Schema(hidden = true)
  public String getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage() {
    return null;
  }

  @Override
  @Schema(hidden = true)
  public void setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage(
      String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage) {

  }
}
