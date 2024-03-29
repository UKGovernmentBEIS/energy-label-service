package uk.gov.beis.els.categories.waterheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromLoadProfileList;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Package of a water heater and solar device energy label calculator")
public class WaterSolarPackagesCalculatorForm extends StandardTemplateForm50Char {

  @FieldPrompt("Water heating energy efficiency of water heater (%)")
  @Digits(integer = 3, fraction = 0, message = "Enter the water heating efficiency (%), up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String waterHeatingEfficiencyPercentage;

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  @ApiValuesFromLoadProfileList(
      serviceClass = WaterHeatersService.class,
      loadProfilesFieldName = "WATER_SOLAR_PACKAGES_LOAD_PROFILES"
  )
  private String declaredLoadProfile;

  @FieldPrompt("Is a hot water storage tank included in this package?")
  @NotNull(message = "Specify if a hot water storage tank is included")
  private Boolean storageTank;

  @FieldPrompt("Annual non-solar heat contribution (kWh)")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual non-solar heat contribution (kWh), up to 4 digits long")
  @Schema(type = "integer")
  @NotNull
  private String annualNonSolarHeatContribution;

  @FieldPrompt("Auxiliary electricity consumption (kWh)")
  @Digits(integer = 4, fraction = 0, message = "Enter the auxiliary electricity consumption (kWh), up to 4 digits long")
  @Schema(type = "integer")
  @NotNull
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
