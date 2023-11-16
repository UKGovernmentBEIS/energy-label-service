package uk.gov.beis.els.categories.refrigeratingappliances.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.refrigeratingappliances.service.RefrigeratingAppliancesService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Household fridges and freezers energy label")
@GroupSequenceProvider(FridgesFreezersFormSequenceProvider.class)
public class FridgesFreezersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = RefrigeratingAppliancesService.class)
  private String efficiencyRating;

  @FieldPrompt("Annual energy consumption (AEC) in kWh per year")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String annualEnergyConsumption;

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re 1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emissions, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String noiseEmissions;

  @FieldPrompt(value = "Does the model have any chill or unfrozen compartments?", hintText = "A chill compartment has a target temperature of 2 degrees Celsius and storage conditions between minus 3 degrees Celsius and 3 degrees Celsius. An unfrozen compartment has a target temperature of 4 degrees Celsius or above.")
  @NotNull(message = "Specify if the model has any chill or unfrozen compartments")
  private Boolean chillCompartment;

  @FieldPrompt("Total volume of chill and unfrozen compartments in litres (l)")
  @Digits(groups = FridgeGroup.class, integer = 3, fraction = 0, message = "Enter the total volume of chill and unfrozen compartments in litres up to 3 digits long")
  @Schema(type = "integer", description = "Total volume of chill and unfrozen compartments in litres (l). Only required if <code>chillCompartment</code> is <code>true</code>.")
  @NotNull(groups = FridgeGroup.class)
  private String chillVolume;

  @FieldPrompt(value = "Does the model have any frozen compartments?", hintText = "A frozen compartment has a target temperature of 0 degrees Celsius or below")
  @NotNull(message = "Specify if the model has any frozen compartments")
  private Boolean frozenCompartment;

  @FieldPrompt("Total volume of frozen compartments in litres (l)")
  @Digits(groups = FreezerGroup.class, integer = 3, fraction = 0, message = "Enter the total volume of frozen compartments in litres, up to 3 digits long")
  @Schema(type = "integer", description = "Total volume of frozen compartments in litres (l). Only required if <code>frozenCompartment</code> is <code>true</code>.")
  @NotNull(groups = FreezerGroup.class)
  private String frozenVolume;

  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class")
  @ApiValuesFromLegislationCategory(
      serviceClass = RefrigeratingAppliancesService.class,
      useSecondaryRange = true
  )
  private String noiseEmissionsClass;

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character"
  )
  @NotNull
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  private String qrCodeUrl;

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

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }

  public Boolean getChillCompartment() {
    return chillCompartment;
  }

  public void setChillCompartment(Boolean chillCompartment) {
    this.chillCompartment = chillCompartment;
  }

  public String getChillVolume() {
    return chillVolume;
  }

  public void setChillVolume(String chillVolume) {
    this.chillVolume = chillVolume;
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

  public String getNoiseEmissionsClass() {
    return noiseEmissionsClass;
  }

  public void setNoiseEmissionsClass(String noiseEmissionsClass) {
    this.noiseEmissionsClass = noiseEmissionsClass;
  }

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
