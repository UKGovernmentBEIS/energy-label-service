package uk.co.fivium.els.categories.localspaceheaters.model;

import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@StaticProductText("<p>To generate a label for a local space heater, enter the product information in the form below.</p>" +
  "<p>Energy labels for local space heaters should be at least 105mm x 200mm when printed. The label should then be attached to the front or top of the product so that it is clearly visible.</p>")
@GroupSequenceProvider(LocalSpaceHeatersFormSequenceProvider.class)
public class LocalSpaceHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("The energy efficiency class of the model")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("The direct heat output in kW")
  @Digits(integer = 2, fraction = 1, message = "Enter the direct heat output, up to 2 digits long with an optional decimal place")
  private String directHeatOutput;

  @FieldPrompt("Is this model a local space heater with transfer to a fluid?")
  @NotNull(message = "Select whether this model can transfer heat to a fluid")
  private Boolean fluidTransfer;

  @FieldPrompt("The indirect heat output in kW")
  @Digits(groups = HeatTransferGroup.class, integer = 2, fraction = 1, message = "Enter the indirect heat output, up to 2 digits long with an optional decimal place")
  private String indirectHeatOutput;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getDirectHeatOutput() {
    return directHeatOutput;
  }

  public void setDirectHeatOutput(String directHeatOutput) {
    this.directHeatOutput = directHeatOutput;
  }

  public Boolean getFluidTransfer() {
    return fluidTransfer;
  }

  public void setFluidTransfer(Boolean fluidTransfer) {
    this.fluidTransfer = fluidTransfer;
  }

  public String getIndirectHeatOutput() {
    return indirectHeatOutput;
  }

  public void setIndirectHeatOutput(String indirectHeatOutput) {
    this.indirectHeatOutput = indirectHeatOutput;
  }
}
