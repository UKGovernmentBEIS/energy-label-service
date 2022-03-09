package uk.gov.beis.els.categories.spaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Packages of space heater, temperature control and solar device energy label calculator - Boiler")
@GroupSequenceProvider(BoilerPackagesCalculatorFormSequenceProvider.class)
public class BoilerPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  @Schema(hidden = true)
  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.BOILER;

  @FieldPrompt("Supplementary heat pump installed?")
  @NotNull(message = "Specify if the package includes a supplementary heat pump")
  private boolean hasSupplementaryHeatPump;

  @FieldPrompt("Rated heat output (Prated) of the supplementary heat pump (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the heat output of the supplementary heat pump",
            groups = SupplementaryHeatPumpGroup.class)
  @Schema(type = "integer")
  @NotNull(groups = SupplementaryHeatPumpGroup.class, message = "Enter the heat output of the supplementary heat pump")
  private String supplementaryHeatPumpHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of supplementary heat pump (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of supplementary heat pump",
            groups = SupplementaryHeatPumpGroup.class)
  @Schema(type = "integer")
  @NotNull(groups = SupplementaryHeatPumpGroup.class, message = "Enter the seasonal space heating energy efficiency of supplementary heat pum")
  private String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;

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
  public boolean isHasSupplementaryHeatPump() {
    return hasSupplementaryHeatPump;
  }

  @Override
  public void setHasSupplementaryHeatPump(boolean hasSupplementaryHeatPump) {
    this.hasSupplementaryHeatPump = hasSupplementaryHeatPump;
  }

  @Override
  public String getSupplementaryHeatPumpHeatOutput() {
    return supplementaryHeatPumpHeatOutput;
  }

  @Override
  public void setSupplementaryHeatPumpHeatOutput(String supplementaryHeatPumpHeatOutput) {
    this.supplementaryHeatPumpHeatOutput = supplementaryHeatPumpHeatOutput;
  }

  @Override
  public String getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage() {
    return supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;
  }

  @Override
  public void setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage(
      String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage) {
    this.supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage = supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;
  }
}
