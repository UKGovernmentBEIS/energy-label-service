package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm40Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.service.RefrigeratorsDirectSalesService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Ice cream freezers energy label")
public class IceCreamFreezersForm extends StandardTemplateForm40Char {
  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @Schema(description = "Energy efficiency class for ice cream freezers")
  @ApiValuesFromLegislationCategory(serviceClass = RefrigeratorsDirectSalesService.class)
  private String efficiencyRating;

  @FieldPrompt("The annual electricity consumption in kWh in terms of final energy consumption per year")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual energy consumption, up to 4 digits long")
  @Schema(type = "integer")
  @NotNull
  private String annualEnergyConsumption;

  @FieldPrompt("The sum of the net volumes, expressed in litres, of all compartments functioning at frozen operating temperature")
  @Digits(integer = 4, fraction = 0, message = "Enter the total volume of frozen compartments, up to 4 digits long")
  @Schema(type = "integer")
  @NotNull
  private String capacity;

  @FieldPrompt("The highest average compartment temperature, expressed in degrees Celsius, of all compartments functioning at frozen operating temperature")
  @Digits(integer = 2, fraction = 0, message = "Enter the highest average compartment temperature, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String compartmentTemp;

  @FieldPrompt("The maximum ambient temperature expressed in degrees Celsius")
  @Digits(integer = 2, fraction = 0, message = "Enter the maximum ambient temperature, up to 2 digits long")
  @Schema(type = "integer")
  @NotNull
  private String maxAmbientTemp;

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @NotNull
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

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public String getCompartmentTemp() {
    return compartmentTemp;
  }

  public void setCompartmentTemp(String compartmentTemp) {
    this.compartmentTemp = compartmentTemp;
  }

  public String getMaxAmbientTemp() {
    return maxAmbientTemp;
  }

  public void setMaxAmbientTemp(String maxAmbientTemp) {
    this.maxAmbientTemp = maxAmbientTemp;
  }

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
