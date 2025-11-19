package uk.gov.beis.els.categories.tumbledryers.model.rescaled;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.tumbledryers.service.RescaledTumbleDryersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@GroupSequenceProvider(RescaledTumbleDryerFromSequenceProvider.class)
public class RescaledTumbleDryersForm extends StandardTemplateForm30Char {

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @NotNull
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  private String qrCodeUrl;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = RescaledTumbleDryersService.class)
  private String efficiencyRating;

  @FieldPrompt("Weighted average energy consumption per 100 drying cycles in kWh, rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter the weighted average energy consumption, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String energyConsumptionPer100Cycles;
  
  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class")
  @ApiValuesFromLegislationCategory(
      serviceClass = RescaledTumbleDryersService.class,
      useSecondaryRange = true
  )
  private String noiseEmissionsClass;

  @FieldPrompt("Airborne acoustical noise emission of the drying cycle of the eco programme in dB(A) re 1 pW, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emission, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String noiseEmissions;

  @FieldPrompt("Rated capacity for the eco programme at full load, in kg")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  @NotNull
  private String ecoCapacity;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the eco programme duration, up to 1 digit. If the eco programme duration is under 1 hour, enter 0")
  @Schema(
      type = "integer",
      description = "The number of hours for the eco programme duration, up to 1 digit. If the eco programme duration is under 1 hour, enter 0"
  )
  @NotNull
  private String programmeDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the eco programme duration, between 0 and 59")
  @Schema(
      type = "integer",
      description = "The number of minutes for the eco programme duration, between 0 and 59"
  )
  @NotNull
  private String programmeDurationMinutes;

  @FieldPrompt(value = "Is this a condensing tumble dryer?", hintText = "This includes heat pump tumble dryers")
  @NotNull(message = "Specify if this is a condensing tumble dryer")
  private Boolean isCondensing;

  @FieldPrompt("Condensation efficiency class")
  @NotBlank(message = "Select an condensation efficiency class", groups = CondensingTumbleDryerGroup.class)
  @ApiValuesFromLegislationCategory(
      serviceClass = RescaledTumbleDryersService.class,
      useSecondaryRange = true
  )
  @Schema(type = "string", description = "Condensation efficiency class. Only required if <code>isCondensing</code> is <code>true</code>.")
  private String condensationEfficiencyClass;

  @FieldPrompt("Condensation efficiency, as a percentage, rounded to the nearest integer")
  @Digits(groups = CondensingTumbleDryerGroup.class, integer = 2, fraction = 0, message = "Enter the condensation efficiency as a percentage, up to 2 digits long")
  @Schema(type = "integer", description = "Condensation efficiency, as a percentage, rounded to the nearest integer. Only required if <code>isCondensing</code> is <code>true</code>.")
  @NotNull(groups = CondensingTumbleDryerGroup.class)
  private String condensationEfficiencyPercentage;
  
  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getEnergyConsumptionPer100Cycles() {
    return energyConsumptionPer100Cycles;
  }

  public void setEnergyConsumptionPer100Cycles(String energyConsumptionPer100Cycles) {
    this.energyConsumptionPer100Cycles = energyConsumptionPer100Cycles;
  }

  public String getNoiseEmissionsClass() {
    return noiseEmissionsClass;
  }

  public void setNoiseEmissionsClass(String noiseEmissionsClass) {
    this.noiseEmissionsClass = noiseEmissionsClass;
  }

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }

  public String getEcoCapacity() {
    return ecoCapacity;
  }

  public void setEcoCapacity(String ecoCapacity) {
    this.ecoCapacity = ecoCapacity;
  }

  public String getProgrammeDurationHours() {
    return programmeDurationHours;
  }

  public void setProgrammeDurationHours(String programmeDurationHours) {
    this.programmeDurationHours = programmeDurationHours;
  }

  public String getProgrammeDurationMinutes() {
    return programmeDurationMinutes;
  }

  public void setProgrammeDurationMinutes(String programmeDurationMinutes) {
    this.programmeDurationMinutes = programmeDurationMinutes;
  }

  public Boolean getIsCondensing() {
    return isCondensing;
  }

  public void setIsCondensing(Boolean condensing) {
    isCondensing = condensing;
  }

  public String getCondensationEfficiencyClass() {
    return condensationEfficiencyClass;
  }

  public void setCondensationEfficiencyClass(String condensationEfficiencyClass) {
    this.condensationEfficiencyClass = condensationEfficiencyClass;
  }

  public String getCondensationEfficiencyPercentage() {
    return condensationEfficiencyPercentage;
  }

  public void setCondensationEfficiencyPercentage(String condensationEfficiencyPercentage) {
    this.condensationEfficiencyPercentage = condensationEfficiencyPercentage;
  }
}
