package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.StandardTemplateForm20Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@GroupSequenceProvider(DisplayCabinetsFormSequenceProvider.class)
public class DisplayCabinetsForm extends StandardTemplateForm20Char {
  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The annual electricity consumption in kWh in terms of final energy consumption per year")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Does this model have any display areas functioning at chilled operating temperature?")
  @NotNull(message = "Select whether this model has chilled display areas")
  private Boolean chilledCompartment;

  @FieldPrompt("The sum of the display areas, expressed in square metres, of all compartments functioning at chilled operating temperature")
  @Digits(groups = FridgeGroup.class, integer = 4, fraction = 2, message = "Enter the total volume of chilled compartments, up to 4 digits long with up to 2 decimal places")
  private String fridgeCapacity;

  @FieldPrompt("The highest temperature, expressed in degrees Celsius, of the warmest M-package of the compartment(s) with chilled operating temperatures")
  @Digits(groups = FridgeGroup.class, integer = 2, fraction = 0, message = "Enter the highest temperature of the warmest M-package in the chilled compartments, up to 2 digits long")
  private String fridgeMaxTemp;

  @FieldPrompt("The lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages, expressed in degrees Celsius of the compartment(s) with chilled operating temperatures")
  @Digits(groups = FridgeGroup.class, integer = 2, fraction = 0, message = "Enter lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages in the chilled compartments, up to 2 digits long")
  private String fridgeMinTemp;

  @FieldPrompt("Does this model have any display areas functioning at frozen operating temperature?")
  @NotNull(message = "Select whether this model has frozen display areas")
  private Boolean frozenCompartment;

  @FieldPrompt("The sum of the display areas, expressed in square metres, of all display areas functioning at frozen operating temperature")
  @Digits(groups = FreezerGroup.class, integer = 4, fraction = 2, message = "Enter the total volume of chilled compartments, up to 4 digits long with up to 2 decimal places")
  private String freezerCapacity;

  @FieldPrompt("The highest temperature, expressed in degrees Celsius, of the warmest M-package of the compartment(s) with frozen operating temperatures")
  @Digits(groups = FreezerGroup.class, integer = 2, fraction = 0, message = "Enter the highest temperature of the warmest M-package in the frozen compartments, up to 2 digits long")
  private String freezerMaxTemp;

  @FieldPrompt("The lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages, expressed in degrees Celsius of the compartment(s) with frozen operating temperatures")
  @Digits(groups = FreezerGroup.class, integer = 2, fraction = 0, message = "Enter lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages in the frozen compartments, up to 2 digits long")
  private String freezerMinTemp;

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

  public Boolean getChilledCompartment() {
    return chilledCompartment;
  }

  public void setChilledCompartment(Boolean chilledCompartment) {
    this.chilledCompartment = chilledCompartment;
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

  public String getFridgeMinTemp() {
    return fridgeMinTemp;
  }

  public void setFridgeMinTemp(String fridgeMinTemp) {
    this.fridgeMinTemp = fridgeMinTemp;
  }

  public Boolean getFrozenCompartment() {
    return frozenCompartment;
  }

  public void setFrozenCompartment(Boolean frozenCompartment) {
    this.frozenCompartment = frozenCompartment;
  }

  public String getFreezerCapacity() {
    return freezerCapacity;
  }

  public void setFreezerCapacity(String freezerCapacity) {
    this.freezerCapacity = freezerCapacity;
  }

  public String getFreezerMaxTemp() {
    return freezerMaxTemp;
  }

  public void setFreezerMaxTemp(String freezerMaxTemp) {
    this.freezerMaxTemp = freezerMaxTemp;
  }

  public String getFreezerMinTemp() {
    return freezerMinTemp;
  }

  public void setFreezerMinTemp(String freezerMinTemp) {
    this.freezerMinTemp = freezerMinTemp;
  }
}
