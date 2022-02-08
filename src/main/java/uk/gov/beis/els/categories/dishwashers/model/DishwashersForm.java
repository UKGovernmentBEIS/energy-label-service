package uk.gov.beis.els.categories.dishwashers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import org.hibernate.validator.constraints.Range;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.dishwashers.service.DishwashersService;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Dishwasher energy label")
@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. If it's a built-in dishwasher it doesn't have to be attached to the product, but it must still be easy to see. Labels must be at least 96mm x 192mm when printed.")
public class DishwashersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re 1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emission, up to 2 digits long")
  @Schema(type = "integer")
  @NotBlank
  private String noiseEmissions;

  @FieldPrompt("Rated capacity (number of standard place settings) for the eco programme")
  @Digits(integer = 2, fraction = 0, message = "Enter a rated capacity, up to 2 digits long")
  @Schema(type = "integer")
  @NotBlank
  private String ecoCapacity;

  @FieldPrompt("Eco programme energy consumption (EPEC) in kWh per 100 cycles")
  @Digits(integer = 3, fraction = 0, message = "Enter an eco programme energy consumption, up to 3 digits long")
  @Schema(type = "integer")
  @NotBlank
  private String energyConsumptionPer100Cycles;

  @FieldPrompt("Eco programme water consumption (EPWC) in litres per cycle")
  @Digits(integer = 2, fraction = 1, message = "Enter an eco programme water consumption, up to 2 digits with an optional decimal place")
  @Schema(type = "number")
  @NotBlank
  private String waterConsumptionPerCycle;

  @FieldPrompt("Hours")
  @Digits(integer = 1, fraction = 0, message = "Enter a number of hours for the eco programme duration, up to 1 digit. If the eco programme duration is under 1 hour, enter 0")
  @Schema(
      type = "integer",
      description = "Enter a number of hours for the eco programme duration, up to 1 digit. If the eco programme duration is under 1 hour, enter 0"
  )
  @NotBlank
  private String programmeDurationHours;

  @FieldPrompt("Minutes")
  @Range(min = 0, max = 59, message = "Enter a number of minutes for the eco programme duration, between 0 and 59")
  @Schema(
      type = "integer",
      description = "Enter a number of minutes for the eco programme duration, between 0 and 59"
  )
  @NotBlank
  private String programmeDurationMinutes;

  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class")
  @ApiValuesFromLegislationCategory(
      serviceClass = DishwashersService.class,
      useSecondaryRange = true
  )
  private String noiseEmissionsClass;

  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = DishwashersService.class)
  private String efficiencyRating;

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @NotBlank
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  private String qrCodeUrl;

  public String getEcoCapacity() {
    return ecoCapacity;
  }

  public void setEcoCapacity(String ecoCapacity) {
    this.ecoCapacity = ecoCapacity;
  }

  public String getEnergyConsumptionPer100Cycles() {
    return energyConsumptionPer100Cycles;
  }

  public void setEnergyConsumptionPer100Cycles(String energyConsumptionPer100Cycles) {
    this.energyConsumptionPer100Cycles = energyConsumptionPer100Cycles;
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

  public String getNoiseEmissionsClass() {
    return noiseEmissionsClass;
  }

  public void setNoiseEmissionsClass(String noiseEmissionsClass) {
    this.noiseEmissionsClass = noiseEmissionsClass;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
