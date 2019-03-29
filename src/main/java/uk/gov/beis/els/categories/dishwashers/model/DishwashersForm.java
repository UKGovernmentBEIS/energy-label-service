package uk.gov.beis.els.categories.dishwashers.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 110mm x 220mm when printed.")
public class DishwashersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Annual energy consumption (AEC) in kWh per year")
  @Digits(integer = 3, fraction = 0, message = "Enter an annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Annual water consumption (AWC) in litres per year")
  @Digits(integer = 4, fraction = 0, message = "Enter an annual water consumption, up to 4 digits long")
  private String annualWaterConsumption;

  @FieldPrompt("Rated capacity (number of standard place settings) for the standard cleaning cycle")
  @Digits(integer = 2, fraction = 0, message = "Enter a rated capacity, up to 2 digits long")
  private String capacity;

  @FieldPrompt("Drying efficiency class")
  @NotBlank(message = "Select a drying efficiency class")
  private String dryingEfficiencyRating;

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re 1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emission, up to 2 digits long")
  private String noiseEmissions;

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

  public String getAnnualWaterConsumption() {
    return annualWaterConsumption;
  }

  public void setAnnualWaterConsumption(String annualWaterConsumption) {
    this.annualWaterConsumption = annualWaterConsumption;
  }

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public String getDryingEfficiencyRating() {
    return dryingEfficiencyRating;
  }

  public void setDryingEfficiencyRating(String dryingEfficiencyRating) {
    this.dryingEfficiencyRating = dryingEfficiencyRating;
  }

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }
}
