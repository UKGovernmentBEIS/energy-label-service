package uk.gov.beis.els.categories.refrigeratorsdirectsales.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm40Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.service.RefrigeratorsDirectSalesService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Supermarket refrigerator, freezer cabinets or gelato-scooping cabinets energy label")
@GroupSequenceProvider(DisplayCabinetsFormSequenceProvider.class)
public class DisplayCabinetsForm extends StandardTemplateForm40Char {
  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @Schema(description = "Energy efficiency class for supermarket refrigerators, freezer cabinets or gelato-scooping cabinets")
  @ApiValuesFromLegislationCategory(serviceClass = RefrigeratorsDirectSalesService.class)
  private String efficiencyRating;

  @FieldPrompt("The annual electricity consumption in kWh in terms of final energy consumption per year")
  @Digits(integer = 5, fraction = 0, message = "Enter the annual energy consumption, up to 5 digits long")
  @Schema(type = "integer")
  private String annualEnergyConsumption;

  @FieldPrompt("Does this model have any display areas functioning at chilled operating temperature?")
  @NotNull(message = "Select whether this model has chilled display areas")
  private Boolean chilledCompartment;

  @FieldPrompt("The sum of the display areas, expressed in square metres, of all compartments functioning at chilled operating temperature")
  @Digits(groups = FridgeGroup.class, integer = 4, fraction = 2, message = "Enter the total display area of chilled compartments, up to 4 digits long with up to 2 decimal places")
  @Schema(type = "number")
  private String fridgeCapacity;

  @FieldPrompt("The highest temperature, expressed in degrees Celsius, of the warmest M-package of the compartment(s) with chilled operating temperatures")
  @Digits(groups = FridgeGroup.class, integer = 2, fraction = 0, message = "Enter the highest temperature of the warmest M-package in the chilled compartments, up to 2 digits long")
  @Schema(type = "integer")
  private String fridgeMaxTemp;

  @FieldPrompt("The lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages, expressed in degrees Celsius of the compartment(s) with chilled operating temperatures")
  @Digits(groups = FridgeGroup.class, integer = 2, fraction = 0, message = "Enter lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages in the chilled compartments, up to 2 digits long")
  @Schema(type = "integer")
  private String fridgeMinTemp;

  @FieldPrompt("Does this model have any display areas functioning at frozen operating temperature?")
  @NotNull(message = "Select whether this model has frozen display areas")
  private Boolean frozenCompartment;

  @FieldPrompt("The sum of the display areas, expressed in square metres, of all display areas functioning at frozen operating temperature")
  @Digits(groups = FreezerGroup.class, integer = 4, fraction = 2, message = "Enter the total display area of frozen compartments, up to 4 digits long with up to 2 decimal places")
  @Schema(type = "number")
  private String freezerCapacity;

  @FieldPrompt("The highest temperature, expressed in degrees Celsius, of the warmest M-package of the compartment(s) with frozen operating temperatures")
  @Digits(groups = FreezerGroup.class, integer = 2, fraction = 0, message = "Enter the highest temperature of the warmest M-package in the frozen compartments, up to 2 digits long")
  @Schema(type = "integer")
  private String freezerMaxTemp;

  @FieldPrompt("The lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages, expressed in degrees Celsius of the compartment(s) with frozen operating temperatures")
  @Digits(groups = FreezerGroup.class, integer = 2, fraction = 0, message = "Enter lowest temperature of the coldest M-package, or the highest minimum temperature of all M-packages in the frozen compartments, up to 2 digits long")
  @Schema(type = "integer")
  private String freezerMinTemp;

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @Schema(description = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character")
  @NotBlank
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

  public Boolean getChilledCompartment() {
    return chilledCompartment;
  }

  public void setChilledCompartment(Boolean chilledCompartment) {
    this.chilledCompartment = chilledCompartment;
  }

  public String getFridgeCapacity() {
    return fridgeCapacity;
  }

  public void setFridgeCapacity(String fridgeCapacity) {
    this.fridgeCapacity = fridgeCapacity;
  }

  public String getFridgeMaxTemp() {
    return fridgeMaxTemp;
  }

  public void setFridgeMaxTemp(String fridgeMaxTemp) {
    this.fridgeMaxTemp = fridgeMaxTemp;
  }

  public String getFridgeMinTemp() {
    return fridgeMinTemp;
  }

  public void setFridgeMinTemp(String fridgeMinTemp) {
    this.fridgeMinTemp = fridgeMinTemp;
  }

  public Boolean getFrozenCompartment() {
    return frozenCompartment;
  }

  public void setFrozenCompartment(Boolean frozenCompartment) {
    this.frozenCompartment = frozenCompartment;
  }

  public String getFreezerCapacity() {
    return freezerCapacity;
  }

  public void setFreezerCapacity(String freezerCapacity) {
    this.freezerCapacity = freezerCapacity;
  }

  public String getFreezerMaxTemp() {
    return freezerMaxTemp;
  }

  public void setFreezerMaxTemp(String freezerMaxTemp) {
    this.freezerMaxTemp = freezerMaxTemp;
  }

  public String getFreezerMinTemp() {
    return freezerMinTemp;
  }

  public void setFreezerMinTemp(String freezerMinTemp) {
    this.freezerMinTemp = freezerMinTemp;
  }

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
