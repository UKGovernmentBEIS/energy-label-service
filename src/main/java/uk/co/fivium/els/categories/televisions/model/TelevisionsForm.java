package uk.co.fivium.els.categories.televisions.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class TelevisionsForm extends StandardTemplateForm30Char {

  @FieldPrompt("When was or will your product placed on the market?")
  @NotBlank(message = "Specify when your product was or will be on the market")
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator")
  private String efficiencyRating;

  @FieldPrompt("Is there a visible power switch which puts the television in a condition with power consumption not exceeding 0.01 Watts?")
  @NotNull(message = "Specify if there is a visible power switch")
  private Boolean powerSwitch;

  @FieldPrompt("On-mode power consumption in Watts")
  @Digits(integer = 3, fraction = 0, message = "Enter the power consumption, up to 3 digits long")
  private String powerConsumption;

  @FieldPrompt("Annual on-mode energy consumption [kWh/annum]")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Visible screen size in diagonal in centimeters")
  @Digits(integer = 3, fraction = 0, message = "Enter the screen size in centimeters, up to 3 digits long")
  private String screenSizeCm;

  @FieldPrompt("Visible screen size in diagonal in inches")
  @Digits(integer = 3, fraction = 0, message = "Enter the screen size in inches, up to 3 digits long")
  private String screenSizeInch;

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

  public Boolean getPowerSwitch() {
    return powerSwitch;
  }

  public void setPowerSwitch(Boolean powerSwitch) {
    this.powerSwitch = powerSwitch;
  }

  public String getPowerConsumption() {
    return powerConsumption;
  }

  public void setPowerConsumption(String powerConsumption) {
    this.powerConsumption = powerConsumption;
  }

  public String getAnnualEnergyConsumption() {
    return annualEnergyConsumption;
  }

  public void setAnnualEnergyConsumption(String annualEnergyConsumption) {
    this.annualEnergyConsumption = annualEnergyConsumption;
  }

  public String getScreenSizeCm() {
    return screenSizeCm;
  }

  public void setScreenSizeCm(String screenSizeCm) {
    this.screenSizeCm = screenSizeCm;
  }

  public String getScreenSizeInch() {
    return screenSizeInch;
  }

  public void setScreenSizeInch(String screenSizeInch) {
    this.screenSizeInch = screenSizeInch;
  }
}
