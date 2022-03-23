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

  @FieldPrompt("Does this package include a supplementary heat pump?")
  @NotNull(message = "Specify if the package includes a supplementary heat pump")
  private Boolean hasSupplementaryHeatPump;

  @FieldPrompt("Rated heat output (Prated) of the supplementary heat pump (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the heat output of the supplementary heat pump, up to 4 digits long",
            groups = SupplementaryHeatPumpGroup.class)
  @Schema(type = "integer")
  @NotNull(groups = SupplementaryHeatPumpGroup.class, message = "Enter the heat output of the supplementary heat pump")
  private String supplementaryHeatPumpHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of supplementary heat pump (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of supplementary heat pump, up to 3 digits long",
            groups = SupplementaryHeatPumpGroup.class)
  @Schema(type = "integer")
  @NotNull(groups = SupplementaryHeatPumpGroup.class, message = "Enter the seasonal space heating energy efficiency of supplementary heat pump")
  private String supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage;

  @Override
  public PreferentialHeaterTypes getPreferentialHeaterType() {
    return preferentialHeaterType;
  }

  public Boolean getHasSupplementaryHeatPump() {
    return hasSupplementaryHeatPump;
  }

  public void setHasSupplementaryHeatPump(Boolean hasSupplementaryHeatPump) {
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
}
