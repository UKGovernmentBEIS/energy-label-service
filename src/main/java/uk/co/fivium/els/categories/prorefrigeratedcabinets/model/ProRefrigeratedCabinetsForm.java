package uk.co.fivium.els.categories.prorefrigeratedcabinets.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

@StaticProductText("You must display the label so that it’s easy to see and clearly related to the product. It must be at least 110mm x 220mm when printed.")
@GroupSequenceProvider(ProRefrigeratedCabinetsSequenceProvider.class)
public class ProRefrigeratedCabinetsForm extends StandardTemplateForm30Char {

  @FieldPrompt("When was the product first placed on the market?")
  @NotBlank(message = "Specify when your product was first placed on the market", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The annual electricity consumption in kWh in terms of final energy consumption per year")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Does this model have any compartments functioning at chilled operating temperature?")
  @NotNull(message = "Select whether this model has chilled compartments")
  private Boolean chilledCompartment;

  @FieldPrompt("The sum of the net volumes, expressed in litres, of all chilled compartments functioning at chilled operating temperature")
  @Digits(groups = FridgeGroup.class, integer = 4, fraction = 0, message = "Enter the total volume of chilled compartments, up to 4 digits long")
  private String chilledVolume;

  @FieldPrompt("Does this model have any compartments functioning at frozen operating temperature (or compartments declared as multi-use)?")
  @NotNull(message = "Select whether this model has frozen or multi-use compartments")
  private Boolean frozenCompartment;

  @FieldPrompt("The sum of the net volumes, expressed in litres, of all compartments functioning at frozen operating temperature (and of all compartments declared as multi-use)")
  @Digits(groups = FreezerGroup.class, integer = 4, fraction = 0, message = "Enter the total volume of frozen or multi-use compartments, up to 4 digits long")
  private String frozenVolume;

  @FieldPrompt("The climate class")
  @NotNull(message = "Select a climate class")
  private String climateClass;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

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

  public String getChilledVolume() {
    return chilledVolume;
  }

  public void setChilledVolume(String chilledVolume) {
    this.chilledVolume = chilledVolume;
  }

  public Boolean getFrozenCompartment() {
    return frozenCompartment;
  }

  public void setFrozenCompartment(Boolean frozenCompartment) {
    this.frozenCompartment = frozenCompartment;
  }

  public String getFrozenVolume() {
    return frozenVolume;
  }

  public void setFrozenVolume(String frozenVolume) {
    this.frozenVolume = frozenVolume;
  }

  public String getClimateClass() {
    return climateClass;
  }

  public void setClimateClass(String climateClass) {
    this.climateClass = climateClass;
  }
}
