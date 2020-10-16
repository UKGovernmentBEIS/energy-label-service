package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.StandardTemplateForm40Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@GroupSequenceProvider(VendingMachinesFormSequenceProvider.class)
public class VendingMachinesForm extends StandardTemplateForm40Char {
  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The annual electricity consumption in kWh in terms of final energy consumption per year")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("The sum of the net volumes, expressed in litres, of all compartments functioning at chilled operating temperature")
  @Digits(integer = 4, fraction = 0, message = "Enter the total volume of chilled compartments, up to 4 digits long")
  private String fridgeCapacity;

  @FieldPrompt("The maximum measured product temperature, expressed in degrees Celsius, of the compartment(s) with chilled operating temperatures")
  @Digits(integer = 2, fraction = 0, message = "Enter the maximum measured product temperature in the chilled compartments, up to 2 digits long")
  private String fridgeMaxTemp;

  @FieldPrompt("Does this model have any compartments functioning at frozen operating temperature?")
  @NotNull(message = "Select whether this model has frozen compartments")
  private Boolean frozenCompartment;

  @FieldPrompt("The maximum measured product temperature, expressed in degrees Celsius, of the compartment(s) with frozen operating temperatures")
  @Digits(groups = FreezerGroup.class, integer = 2, fraction = 0, message = "Enter the maximum measured product temperature in the frozen compartments, up to 2 digits long")
  private String freezerMaxTemp;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getAnnualEnergyConsumption() {
    return annualEnergyConsumption;
  }

  public void setAnnualEnergyConsumption(String annualEnergyConsumption) {
    this.annualEnergyConsumption = annualEnergyConsumption;
  }

  public String getFridgeCapacity() {
    return fridgeCapacity;
  }

  public void setFridgeCapacity(String fridgeCapacity) {
    this.fridgeCapacity = fridgeCapacity;
  }

  public String getFridgeMaxTemp() {
    return fridgeMaxTemp;
  }

  public void setFridgeMaxTemp(String fridgeMaxTemp) {
    this.fridgeMaxTemp = fridgeMaxTemp;
  }

  public Boolean getFrozenCompartment() {
    return frozenCompartment;
  }

  public void setFrozenCompartment(Boolean frozenCompartment) {
    this.frozenCompartment = frozenCompartment;
  }

  public String getFreezerMaxTemp() {
    return freezerMaxTemp;
  }

  public void setFreezerMaxTemp(String freezerMaxTemp) {
    this.freezerMaxTemp = freezerMaxTemp;
  }
}
