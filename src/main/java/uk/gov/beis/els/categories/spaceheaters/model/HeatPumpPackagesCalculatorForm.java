package uk.gov.beis.els.categories.spaceheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class HeatPumpPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.HEAT_PUMP;

  @FieldPrompt("Seasonal space heating energy efficiency of the heat pump in colder climate (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of heat pump in colder climate")
  private String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;

  @FieldPrompt("Seasonal space heating energy efficiency of heat pump in warmer climate (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of heat pump in warmer climate")
  private String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;

  @FieldPrompt("Is this a low temperature heat pump?")
  @NotNull(message = "Specify if this a low temperature heat pump")
  private boolean lowTemperatureHeatPump;

  @Override
  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
  }

  @Override
  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage() {
    return preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;
  }

  @Override
  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage) {
    this.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage = preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;
  }

  @Override
  public String getPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage() {
    return preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;
  }

  @Override
  public void setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage(
      String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage) {
    this.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage = preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;
  }

  @Override
  public boolean isLowTemperatureHeatPump() {
    return lowTemperatureHeatPump;
  }

  @Override
  public void setLowTemperatureHeatPump(boolean lowTemperatureHeatPump) {
    this.lowTemperatureHeatPump = lowTemperatureHeatPump;
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
