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
import uk.gov.beis.els.categories.common.StandardTemplateForm40Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.washingmachines.service.WashingMachinesService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Washer-dryers energy label")
@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in washer-dryer it doesn't have to be attached to the product, but it must still be easy to see. It must be at least 96mm x 192mm when printed.")
public class WasherDryerForm extends StandardTemplateForm40Char {

  @FieldPrompt("Energy efficiency class for the complete cycle")
  @NotBlank(message = "Select an energy efficiency class for the complete cycle", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = WashingMachinesService.class)
  private String completeCycleEfficiencyRating;

  @FieldPrompt("Energy efficiency class for the washing cycle")
  @NotBlank(message = "Select an energy efficiency class for the washing cycle")
  @ApiValuesFromLegislationCategory(serviceClass = WashingMachinesService.class)
  private String washingCycleEfficiencyRating;

  @FieldPrompt("Weighted energy consumption per 100 complete cycles in kWh, rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter a weighted energy consumption per 100 complete cycles, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String completeCycleEnergyConsumption;

  @FieldPrompt("Weighted energy consumption per 100 washing cycles in kWh, rounded to the nearest integer")
  @Digits(integer = 3, fraction = 0, message = "Enter a weighted energy consumption per 100 washing cycles, up to 3 digits long")
  @Schema(type = "integer")
  @NotNull
  private String washingCycleEnergyConsumption;

  @FieldPrompt("Rated capacity for the complete cycle in Kg")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity for the complete cycle, up to 2 digits long with an optional decimal place")
  @Schema(type = "number")
  @NotNull
  private String completeCycleCapacity;

  @FieldPrompt("Rated capacity for the washing cycle in Kg")
  @Digits(integer = 2, fraction = 1, message = "Enter a rated capacity for the washing cycle, up to 2 digits long with an optional decimal place")
  @Schema(type = "number")
  @NotNull
  private String washingCycleCapacity;

  @FieldPrompt("Weighted water consumption per complete cycle in litres, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter a weighted water consumption per complete cycle, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String completeCycleWaterConsumption;

  @FieldPrompt("Weighted water consumption per washing cycle in litres, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter a weighted water consumption per washing cycle, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String washingCycleWaterConsumption;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the complete cycle, up to 1 digit. If the complete cycle is under 1 hour, enter 0")
  @Schema(
      type = "integer",
      description = "Number of hours for the complete cycle, up to 1 digit. If the complete cycle is under 1 hour, enter 0"
  )
  @NotNull
  private String completeCycleDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the complete cycle, between 0 and 59")
  @Schema(
      type = "integer",
      description = "Number of minutes for the complete cycle, between 0 and 59"
  )
  @NotNull
  private String completeCycleDurationMinutes;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the washing cycle, up to 1 digit. If the washing cycle is under 1 hour, enter 0")
  @Schema(
      type = "integer",
      description = "Number of hours for the washing cycle, up to 1 digit. If the washing cycle is under 1 hour, enter 0"
  )
  @NotNull
  private String washingCycleDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the washing cycle, between 0 and 59")
  @Schema(
      type = "integer",
      description = "Number of minutes for the washing cycle, between 0 and 59"
  )
  @NotNull
  private String washingCycleDurationMinutes;

  @FieldPrompt("Spin drying efficiency class")
  @NotBlank(message = "Select a spin drying efficiency class")
  @ApiValuesFromRatingClassRange(
      serviceClass = WashingMachinesService.class,
      ratingClassRangeFieldName = "SPIN_DRYING_CLASS_RANGE"
  )
  private String spinDryingEfficiencyRating;

  @FieldPrompt("Airborne acoustic noise emission class of the spinning phase of the eco 40-60 programme")
  @NotBlank(message = "Select an airborne acoustic noise emission class")
  @ApiValuesFromRatingClassRange(
      serviceClass = WashingMachinesService.class,
      ratingClassRangeFieldName = "NOISE_EMISSIONS_CLASS_RANGE"
  )
  private String noiseEmissionClass;

  @FieldPrompt("Airborne acoustic noise emission of the spinning phase of the eco 40-60 programme in dB(A) re 1 pW, rounded to the nearest integer")
  @Digits(integer = 2, fraction = 0, message = "Enter the airborne acoustic noise emission, up to 2 digits")
  @Schema(type = "integer")
  @NotNull
  private String noiseEmissionValue;

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @NotNull
  private String qrCodeUrl;

  public String getCompleteCycleEfficiencyRating() {
    return completeCycleEfficiencyRating;
  }

  public void setCompleteCycleEfficiencyRating(String completeCycleEfficiencyRating) {
    this.completeCycleEfficiencyRating = completeCycleEfficiencyRating;
  }

  public String getWashingCycleEfficiencyRating() {
    return washingCycleEfficiencyRating;
  }

  public void setWashingCycleEfficiencyRating(String washingCycleEfficiencyRating) {
    this.washingCycleEfficiencyRating = washingCycleEfficiencyRating;
  }

  public String getCompleteCycleEnergyConsumption() {
    return completeCycleEnergyConsumption;
  }

  public void setCompleteCycleEnergyConsumption(String completeCycleEnergyConsumption) {
    this.completeCycleEnergyConsumption = completeCycleEnergyConsumption;
  }

  public String getWashingCycleEnergyConsumption() {
    return washingCycleEnergyConsumption;
  }

  public void setWashingCycleEnergyConsumption(String washingCycleEnergyConsumption) {
    this.washingCycleEnergyConsumption = washingCycleEnergyConsumption;
  }

  public String getCompleteCycleCapacity() {
    return completeCycleCapacity;
  }

  public void setCompleteCycleCapacity(String completeCycleCapacity) {
    this.completeCycleCapacity = completeCycleCapacity;
  }

  public String getWashingCycleCapacity() {
    return washingCycleCapacity;
  }

  public void setWashingCycleCapacity(String washingCycleCapacity) {
    this.washingCycleCapacity = washingCycleCapacity;
  }

  public String getCompleteCycleWaterConsumption() {
    return completeCycleWaterConsumption;
  }

  public void setCompleteCycleWaterConsumption(String completeCycleWaterConsumption) {
    this.completeCycleWaterConsumption = completeCycleWaterConsumption;
  }

  public String getWashingCycleWaterConsumption() {
    return washingCycleWaterConsumption;
  }

  public void setWashingCycleWaterConsumption(String washingCycleWaterConsumption) {
    this.washingCycleWaterConsumption = washingCycleWaterConsumption;
  }

  public String getCompleteCycleDurationHours() {
    return completeCycleDurationHours;
  }

  public void setCompleteCycleDurationHours(String completeCycleDurationHours) {
    this.completeCycleDurationHours = completeCycleDurationHours;
  }

  public String getCompleteCycleDurationMinutes() {
    return completeCycleDurationMinutes;
  }

  public void setCompleteCycleDurationMinutes(String completeCycleDurationMinutes) {
    this.completeCycleDurationMinutes = completeCycleDurationMinutes;
  }

  public String getWashingCycleDurationHours() {
    return washingCycleDurationHours;
  }

  public void setWashingCycleDurationHours(String washingCycleDurationHours) {
    this.washingCycleDurationHours = washingCycleDurationHours;
  }

  public String getWashingCycleDurationMinutes() {
    return washingCycleDurationMinutes;
  }

  public void setWashingCycleDurationMinutes(String washingCycleDurationMinutes) {
    this.washingCycleDurationMinutes = washingCycleDurationMinutes;
  }

  public String getSpinDryingEfficiencyRating() {
    return spinDryingEfficiencyRating;
  }

  public void setSpinDryingEfficiencyRating(String spinDryingEfficiencyRating) {
    this.spinDryingEfficiencyRating = spinDryingEfficiencyRating;
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
