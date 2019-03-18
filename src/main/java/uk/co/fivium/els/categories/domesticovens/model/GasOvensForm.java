package uk.co.fivium.els.categories.domesticovens.model;

import uk.co.fivium.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;

public class GasOvensForm extends DomesticOvensForm{

  @FieldPrompt("Energy consumption per cycle expressed in MJ/cycle: heating function (conventional)")
  @Digits(integer = 1, fraction = 2, message = "Enter the consumption per cycle of the heating function, 1 digit with up to 2 decimal places")
  private String conventionalMjConsumption;

  @FieldPrompt("Energy consumption per cycle expressed in MJ/cycle: Forced air convection (if available) ")
  @Digits(groups = FanOvenGroup.class, integer = 1, fraction = 2, message = "Enter the consumption per cycle of the forced air convection, 1 digit with up to 2 decimal places")
  private String convectionMjConsumption;

  public String getConventionalMjConsumption() {
    return conventionalMjConsumption;
  }

  public void setConventionalMjConsumption(String conventionalMjConsumption) {
    this.conventionalMjConsumption = conventionalMjConsumption;
  }

  public String getConvectionMjConsumption() {
    return convectionMjConsumption;
  }

  public void setConvectionMjConsumption(String convectionMjConsumption) {
    this.convectionMjConsumption = convectionMjConsumption;
  }
}
