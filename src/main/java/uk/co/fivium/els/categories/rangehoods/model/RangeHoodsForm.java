package uk.co.fivium.els.categories.rangehoods.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

@StaticProductText("You must display the label so that it’s easy to see and clearly related to the product. It must be at least 110mm x 220mm when printed.")
public class RangeHoodsForm extends StandardTemplateForm30Char {

  @FieldPrompt("When was the product first placed on the market?")
  @NotBlank(message = "Specify when your product was first placed on the market", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator" , groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
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
