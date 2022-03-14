package uk.gov.beis.els.categories.spaceheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.model.meta.FieldPrompt;

@GroupSequenceProvider(BoilerPackagesCalculatorFormSequenceProvider.class)
public class BoilerPackagesCalculatorForm extends SpaceHeaterPackagesCalculatorForm{

  private final PreferentialHeaterTypes preferentialHeaterType = PreferentialHeaterTypes.BOILER;

  @FieldPrompt("Supplementary heat pump installed?")
  @NotNull(message = "Specify if the package includes a supplementary heat pump")
  private Boolean hasSupplementaryHeatPump;

  @FieldPrompt("Rated heat output (Prated) of the supplementary heat pump (in kW)")
  @Digits(integer = 4, fraction = 0, message = "Enter the heat output of the supplementary heat pump",
            groups = SupplementaryHeatPumpGroup.class)
  private String supplementaryHeatPumpHeatOutput;

  @FieldPrompt("Seasonal space heating energy efficiency of supplementary heat pump (in %)")
  @Digits(integer = 3, fraction = 0, message = "Enter the seasonal space heating energy efficiency of supplementary heat pump",
            groups = SupplementaryHeatPumpGroup.class)
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
