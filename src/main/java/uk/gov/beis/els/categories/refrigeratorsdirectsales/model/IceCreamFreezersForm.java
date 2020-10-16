package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.categories.common.StandardTemplateForm40Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class IceCreamFreezersForm extends StandardTemplateForm40Char {
  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("The annual electricity consumption in kWh in terms of final energy consumption per year")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("The sum of the net volumes, expressed in litres, of all compartments functioning at frozen operating temperature")
  @Digits(integer = 4, fraction = 0, message = "Enter the total volume of frozen compartments, up to 4 digits long")
  private String capacity;

  @FieldPrompt("The highest average compartment temperature, expressed in degrees Celsius, of all compartments functioning at frozen operating temperature")
  @Digits(integer = 2, fraction = 0, message = "Enter the highest average compartment temperature, up to 2 digits long")
  private String compartmentTemp;

  @FieldPrompt("The maximum ambient temperature expressed in degrees Celsius")
  @Digits(integer = 2, fraction = 0, message = "Enter the maximum ambient temperature, up to 2 digits long")
  private String maxAmbientTemp;

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

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public String getCompartmentTemp() {
    return compartmentTemp;
  }

  public void setCompartmentTemp(String compartmentTemp) {
    this.compartmentTemp = compartmentTemp;
  }

  public String getMaxAmbientTemp() {
    return maxAmbientTemp;
  }

  public void setMaxAmbientTemp(String maxAmbientTemp) {
    this.maxAmbientTemp = maxAmbientTemp;
  }
}
