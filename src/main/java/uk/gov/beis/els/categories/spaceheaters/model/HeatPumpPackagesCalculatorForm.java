package uk.gov.beis.els.categories.spaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Packages of space heater, temperature control and solar device energy label calculator - Heat pump")
public class HeatPumpPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  @Schema(hidden = true)
  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.HEAT_PUMP;

  @FieldPrompt("Seasonal space heating energy efficiency of the heat pump in colder climate (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of heat pump in colder climate, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull(message = "Enter the seasonal space heating energy efficiency of heat pump in colder climate")
  private String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage;

  @FieldPrompt("Seasonal space heating energy efficiency of heat pump in warmer climate (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of heat pump in warmer climate, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull(message = "Enter the seasonal space heating energy efficiency of heat pump in warmer climate")
  private String preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage;

  @FieldPrompt("Is this a low temperature heat pump?")
  @NotNull(message = "Specify if this a low temperature heat pump")
  private Boolean lowTemperatureHeatPump;

  @Override
  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
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

  public Boolean getLowTemperatureHeatPump() {
    return lowTemperatureHeatPump;
  }

  public void setLowTemperatureHeatPump(Boolean lowTemperatureHeatPump) {
    this.lowTemperatureHeatPump = lowTemperatureHeatPump;
  }
}
