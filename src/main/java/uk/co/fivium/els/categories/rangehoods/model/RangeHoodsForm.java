package uk.co.fivium.els.categories.rangehoods.model;

import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@StaticProductText("<p>To generate a label for a range hood, enter the product information in the form below.</p>" +
    "<p>Energy labels for range hoods should be at least 60mm x 120mm when printed. This label should then be displayed so that it is easily readable and clearly associated with the product.</p>")
public class RangeHoodsForm extends StandardTemplateForm30Char {

  @FieldPrompt("When was or will your product placed on the market?")
  @NotBlank(message = "Specify when your product was or will be on the market")
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator")
  private String efficiencyRating;

  @FieldPrompt("Annual energy consumption - AEC hood (kWh/annum)")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("The Fluid Dynamic Efficiency class")
  @NotBlank(message = "Select a fluid dynamic efficiency class")
  private String fluidClass;

  @FieldPrompt("The Lighting Efficiency class")
  @NotBlank(message = "Select a lighting efficiency class")
  private String lightingClass;

  @FieldPrompt("The Grease Filtering Efficiency class")
  @NotBlank(message = "Select a grease filtering efficiency class")
  private String greaseClass;

  @FieldPrompt("The Noise Value (dB)")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise value, up to 2 digits long")
  private String noiseValue;

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

  public String getFluidClass() {
    return fluidClass;
  }

  public void setFluidClass(String fluidClass) {
    this.fluidClass = fluidClass;
  }

  public String getLightingClass() {
    return lightingClass;
  }

  public void setLightingClass(String lightingClass) {
    this.lightingClass = lightingClass;
  }

  public String getGreaseClass() {
    return greaseClass;
  }

  public void setGreaseClass(String greaseClass) {
    this.greaseClass = greaseClass;
  }

  public String getNoiseValue() {
    return noiseValue;
  }

  public void setNoiseValue(String noiseValue) {
    this.noiseValue = noiseValue;
  }
}
