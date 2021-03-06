package uk.gov.beis.els.categories.televisions.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.televisions.model.validation.HdrGroup;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.televisions.model.validation.TelevisionsFormSequenceProvider;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("<p>You must display the label so that it’s easy to see and clearly related to the product.</p> <p>Old-style labels must be at least 60mm x 120mm when printed.</p><p>New-style rescaled labels must usually be at least 96mm x 192mm when printed. If the diagonal size of the visible screen area is less than 127cm (50 inches) you can scale down the the label, but it must still be at least 57.6mm x 115.2mm. If you scale down the label you must test that the QR code can still be read when printed, for example by using a smartphone camera.</p>")
@GroupSequenceProvider(TelevisionsFormSequenceProvider.class)
public class TelevisionsForm extends StandardTemplateForm30Char {

  @FieldPrompt("What kind of label do you need to create?")
  @NotBlank(message = "Select the kind of label you need to create", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Visible diagonal screen size in centimetres")
  @Digits(integer = 4, fraction = 0, message = "Enter the screen size in centimetres, up to 4 digits long")
  private String screenSizeCm;

  @FieldPrompt("Visible diagonal screen size in inches")
  @Digits(integer = 3, fraction = 0, message = "Enter the screen size in inches, up to 3 digits long")
  private String screenSizeInch;

  // Pre march 2021 fields
  @FieldPrompt("Energy efficiency class indicator")
  @NotBlank(message = "Select an energy efficiency indicator", groups = PreMarch2021Field.class)
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Is there a visible power switch which puts the television in a condition with power consumption not exceeding 0.01 Watts?")
  @NotNull(message = "Specify if there is a visible power switch", groups = PreMarch2021Field.class)
  private Boolean powerSwitch;

  @FieldPrompt("On-mode power consumption in Watts")
  @Digits(integer = 3, fraction = 0, message = "Enter the power consumption, up to 3 digits long", groups = PreMarch2021Field.class)
  private String powerConsumption;

  @FieldPrompt("Annual on-mode energy consumption [kWh/annum]")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long", groups = PreMarch2021Field.class)
  private String annualEnergyConsumption;

  // Post march 2021 fields
  @FieldPrompt("Energy efficiency class for SDR content")
  @NotBlank(message = "Select an energy efficiency class for SDR content", groups = PostMarch2021Field.class)
  @DualModeField
  private String efficiencyRatingSdr;

  @FieldPrompt("On-mode energy consumption in kWh per 1000 hours, when playing SDR content")
  @Digits(integer = 4, fraction = 0, message = "Enter the energy consumption when playing SDR content, up to 4 digits long", groups = PostMarch2021Field.class)
  private String energyConsumptionSdr1000h;

  @FieldPrompt("Can this product display HDR content?")
  @NotNull(message = "Select whether this model can display HDR content", groups = PostMarch2021Field.class)
  private Boolean isHdr;

  @FieldPrompt("Energy efficiency class for HDR content")
  @NotBlank(message = "Select an energy efficiency class for HDR content", groups = HdrGroup.class)
  private String efficiencyRatingHdr;

  @FieldPrompt("On-mode energy consumption in kWh per 1000 hours, when playing HDR content")
  @Digits(integer = 4, fraction = 0, message = "Enter the energy consumption when playing HDR content, up to 4 digits long", groups = HdrGroup.class)
  private String energyConsumptionHdr1000h;

  @FieldPrompt("Horizontal resolution in pixels")
  @Digits(integer = 5, fraction = 0, message = "Enter the horizontal resolution in pixels, up to 5 digits long", groups = PostMarch2021Field.class)
  private String horizontalPixels;

  @FieldPrompt("Vertical resolution in pixels")
  @Digits(integer = 5, fraction = 0, message = "Enter the vertical resolution in pixels, up to 5 digits long", groups = PostMarch2021Field.class)
  private String verticalPixels;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
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

  public String getEfficiencyRatingSdr() {
    return efficiencyRatingSdr;
  }

  public void setEfficiencyRatingSdr(String efficiencyRatingSdr) {
    this.efficiencyRatingSdr = efficiencyRatingSdr;
  }

  public String getEnergyConsumptionSdr1000h() {
    return energyConsumptionSdr1000h;
  }

  public void setEnergyConsumptionSdr1000h(String energyConsumptionSdr1000h) {
    this.energyConsumptionSdr1000h = energyConsumptionSdr1000h;
  }

  public Boolean getIsHdr() {
    return isHdr;
  }

  public void setIsHdr(Boolean hdr) {
    isHdr = hdr;
  }

  public String getEfficiencyRatingHdr() {
    return efficiencyRatingHdr;
  }

  public void setEfficiencyRatingHdr(String efficiencyRatingHdr) {
    this.efficiencyRatingHdr = efficiencyRatingHdr;
  }

  public String getEnergyConsumptionHdr1000h() {
    return energyConsumptionHdr1000h;
  }

  public void setEnergyConsumptionHdr1000h(String energyConsumptionHdr1000h) {
    this.energyConsumptionHdr1000h = energyConsumptionHdr1000h;
  }

  public String getHorizontalPixels() {
    return horizontalPixels;
  }

  public void setHorizontalPixels(String horizontalPixels) {
    this.horizontalPixels = horizontalPixels;
  }

  public String getVerticalPixels() {
    return verticalPixels;
  }

  public void setVerticalPixels(String verticalPixels) {
    this.verticalPixels = verticalPixels;
  }
}
