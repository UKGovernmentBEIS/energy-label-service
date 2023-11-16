package uk.gov.beis.els.categories.washingmachines.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import org.hibernate.validator.constraints.Range;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.ApiValuesFromRatingClassRange;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Washing machines energy label")
@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in washing machine it doesn't have to be attached to the product, but it must still be easy to see. Labels must be at least 96mm x 192mm when printed.")
public class WashingMachinesForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = WashingMachinesService.class)
  private String efficiencyRating;

  @FieldPrompt("Spin-drying efficiency class")
  @NotBlank(message = "Select a spin-drying efficiency class")
  @ApiValuesFromRatingClassRange(
      serviceClass = WashingMachinesService.class,
      ratingClassRangeFieldName = "SPIN_DRYING_CLASS_RANGE"
  )
  private String spinDryingEfficiencyRating;

  @FieldPrompt("Weighted energy consumption per 100 cycles in kWh, rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter a weighted energy consumption per 100 cycles, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String energyConsumptionPer100Cycles;

  @FieldPrompt("Rated capacity in Kg for the the eco 40-60 programme")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity for the eco 40-60 programme, up to 2 digits long with an optional decimal place")
  @Schema(type = "number")
  @NotNull
  private String ecoRatedCapacity;

  @FieldPrompt("Weighted water consumption per cycle in litres, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter a weighted water consumption per cycle, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String waterConsumptionPerCycle;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the eco 40-60 programme, up to 1 digit. If the programme is under 1 hour, enter 0")
  @Schema(
      type = "integer",
      description = "Hours duration of the eco 40-60 programme at rated capacity"
  )
  @NotNull
  private String programmeDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the eco 40-60 programme, between 0 and 59")
  @Schema(
      type = "integer",
      description = "Minutes duration of the eco 40-60 programme at rated capacity"
  )
  @NotNull
  private String programmeDurationMinutes;

  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class")
  @ApiValuesFromRatingClassRange(
      serviceClass = WashingMachinesService.class,
      ratingClassRangeFieldName = "NOISE_EMISSIONS_CLASS_RANGE"
  )
  private String noiseEmissionClass;

  @FieldPrompt("Airborne acoustic noise emission of the spinning phase in dB(A) re 1 pW, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the airborne acoustic noise emission, up to 2 digits")
  @Schema(type = "integer")
  @NotNull
  private String noiseEmissionValue;

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character"
  )
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @NotNull
  private String qrCodeUrl;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getSpinDryingEfficiencyRating() {
    return spinDryingEfficiencyRating;
  }

  public void setSpinDryingEfficiencyRating(String spinDryingEfficiencyRating) {
    this.spinDryingEfficiencyRating = spinDryingEfficiencyRating;
  }

  public String getEnergyConsumptionPer100Cycles() {
    return energyConsumptionPer100Cycles;
  }

  public void setEnergyConsumptionPer100Cycles(String energyConsumptionPer100Cycles) {
    this.energyConsumptionPer100Cycles = energyConsumptionPer100Cycles;
  }

  public String getEcoRatedCapacity() {
    return ecoRatedCapacity;
  }

  public void setEcoRatedCapacity(String ecoRatedCapacity) {
    this.ecoRatedCapacity = ecoRatedCapacity;
  }

  public String getWaterConsumptionPerCycle() {
    return waterConsumptionPerCycle;
  }

  public void setWaterConsumptionPerCycle(String waterConsumptionPerCycle) {
    this.waterConsumptionPerCycle = waterConsumptionPerCycle;
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

  public String getNoiseEmissionClass() {
    return noiseEmissionClass;
  }

  public void setNoiseEmissionClass(String noiseEmissionClass) {
    this.noiseEmissionClass = noiseEmissionClass;
  }

  public String getNoiseEmissionValue() {
    return noiseEmissionValue;
  }

  public void setNoiseEmissionValue(String noiseEmissionValue) {
    this.noiseEmissionValue = noiseEmissionValue;
  }

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
