package uk.gov.beis.els.categories.waterheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class WaterSolarPackagesCalculatorForm extends StandardTemplateForm50Char {

  @FieldPrompt("Water heating efficiency (%)")
  @Digits(integer = 3, fraction = 0, message = "Enter the water heating efficiency (%), up to 3 digits long")
  private String waterHeatingEfficiencyPercentage;

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("Can a hot water storage tank be included in this package?")
  @NotNull(message = "Specify if a hot water storage tank can be included")
  private Boolean storageTank;

  @FieldPrompt("Can a solar collector be included in this package?")
  @NotNull(message = "Specify if a solar collector can be included")
  private Boolean solarCollector = true;

  //TODO check integer=3
  @FieldPrompt("Annual non-solar heat contribution (kWh)")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual non-solar heat contribution (kWh), up to 3 digits long")
  private String annualNonSolarHeatContribution;

  //TODO check integer=3
  @FieldPrompt("Auxiliary electricity consumption (kWh)")
  @Digits(integer = 3, fraction = 0, message = "Enter the auxiliary electricity consumption (kWh), up to 3 digits long")
  private String auxElectricityConsumption;

  public String getWaterHeatingEfficiencyPercentage() {
    return waterHeatingEfficiencyPercentage;
  }

  public void setWaterHeatingEfficiencyPercentage(String waterHeatingEfficiencyPercentage) {
    this.waterHeatingEfficiencyPercentage = waterHeatingEfficiencyPercentage;
  }

  public String getDeclaredLoadProfile() {
    return declaredLoadProfile;
  }

  public void setDeclaredLoadProfile(String declaredLoadProfile) {
    this.declaredLoadProfile = declaredLoadProfile;
  }

  public Boolean getStorageTank() {
    return storageTank;
  }

  public void setStorageTank(Boolean storageTank) {
    this.storageTank = storageTank;
  }

  public Boolean getSolarCollector() {
    return solarCollector;
  }

  public void setSolarCollector(Boolean solarCollector) {
    this.solarCollector = solarCollector;
  }

  public String getAnnualNonSolarHeatContribution() {
    return annualNonSolarHeatContribution;
  }

  public void setAnnualNonSolarHeatContribution(String annualNonSolarHeatContribution) {
    this.annualNonSolarHeatContribution = annualNonSolarHeatContribution;
  }

  public String getAuxElectricityConsumption() {
    return auxElectricityConsumption;
  }

  public void setAuxElectricityConsumption(String auxElectricityConsumption) {
    this.auxElectricityConsumption = auxElectricityConsumption;
  }
}
