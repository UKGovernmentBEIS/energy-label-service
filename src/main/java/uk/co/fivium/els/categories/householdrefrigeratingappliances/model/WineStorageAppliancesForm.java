package uk.co.fivium.els.categories.householdrefrigeratingappliances.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.model.FieldPrompt;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class WineStorageAppliancesForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency indicator for an 'average' climate")
  @NotBlank(message = "Select an energy efficiency indicator")
  private String efficiencyRating;

  @FieldPrompt("Annual energy consumption (AEC) in kWh per year")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Rated capacity in number of standard wine bottles")
  @Digits(integer = 3, fraction = 0, message = "Enter the total capacity of bottles, up to 3 digits long")
  private String bottleCapacity;

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emissions, up to 2 digits long")
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

  public String getBottleCapacity() {
    return bottleCapacity;
  }

  public void setBottleCapacity(String bottleCapacity) {
    this.bottleCapacity = bottleCapacity;
  }

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }
}
