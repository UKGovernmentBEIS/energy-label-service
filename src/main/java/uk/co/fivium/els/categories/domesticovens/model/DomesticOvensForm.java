package uk.co.fivium.els.categories.domesticovens.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.categories.common.StandardTemplateForm40Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

@GroupSequenceProvider(DomesticOvensFormSequenceProvider.class)
public class DomesticOvensForm extends StandardTemplateForm40Char {

  @FieldPrompt("The energy efficiency class of the cavity")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Usable volume of the cavity in litres (L)")
  @Digits(integer = 2, fraction = 0, message = "Enter the volume, up to 2 digits long")
  private String volume;

  @FieldPrompt("Energy consumption per cycle expressed in kWh/cycle: heating function (conventional)")
  @Digits(integer = 1, fraction = 2, message = "Enter the consumption per cycle of the heating function, 1 digit with up to 2 decimal places")
  private String conventionalKwhConsumption;

  @FieldPrompt("Is this a forced air convection oven?")
  @NotNull(message = "Specify if this is a forced air convection oven?")
  private Boolean isFanOven;

  @FieldPrompt("Energy consumption per cycle expressed in kWh/cycle: Forced air convection (if available) ")
  @Digits(groups = FanOvenGroup.class, integer = 1, fraction = 2, message = "Enter the consumption per cycle of the forced air convection, 1 digit with up to 2 decimal places")
  private String convectionKwhConsumption;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getConventionalKwhConsumption() {
    return conventionalKwhConsumption;
  }

  public void setConventionalKwhConsumption(String conventionalKwhConsumption) {
    this.conventionalKwhConsumption = conventionalKwhConsumption;
  }

  public Boolean getIsFanOven() {
    return isFanOven;
  }

  public void setIsFanOven(Boolean fanOven) {
    isFanOven = fanOven;
  }

  public String getConvectionKwhConsumption() {
    return convectionKwhConsumption;
  }

  public void setConvectionKwhConsumption(String convectionKwhConsumption) {
    this.convectionKwhConsumption = convectionKwhConsumption;
  }
}
