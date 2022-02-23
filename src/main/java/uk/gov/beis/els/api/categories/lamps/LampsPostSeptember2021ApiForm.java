package uk.gov.beis.els.api.categories.lamps;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.lamps.model.TemplateColour;
import uk.gov.beis.els.categories.lamps.model.TemplateSize;
import uk.gov.beis.els.categories.lamps.service.LampsService;

@Schema(name = "Lamps and light sources new style energy label")
public class LampsPostSeptember2021ApiForm extends StandardTemplateForm50Char {

  @NotBlank(message = "Select an energy efficiency class")
  @Schema(description = "Energy efficiency class of the application")
  @ApiValuesFromLegislationCategory(
      serviceClass = LampsService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_POST_SEPTEMBER_2021"
  )
  private String efficiencyRating;

  @Digits(integer = 4, fraction = 0, message = "Enter an energy consumption, up to 4 digits long")
  @Schema(
      type = "integer",
      description = "Weighted energy consumption (EC) in kWh per 1000 hours, rounded up to the nearest integer"
  )
  @NotNull
  private String energyConsumption;

  @NotBlank(message = "Select the size of label you need to create")
  @ApiValuesFromEnum(TemplateSize.class)
  @Schema(description = "What size of label do you need to create? You must only use the small label on packaging less than 36mm wide")
  private String templateSize;

  @NotBlank(message = "Select whether the label should be in colour or black and white")
  @ApiValuesFromEnum(TemplateColour.class)
  @Schema(description = "Should the label be in colour or black and white?")
  private String templateColour;

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

  public String getEnergyConsumption() {
    return energyConsumption;
  }

  public void setEnergyConsumption(String energyConsumption) {
    this.energyConsumption = energyConsumption;
  }

  public String getTemplateSize() {
    return templateSize;
  }

  public void setTemplateSize(String templateSize) {
    this.templateSize = templateSize;
  }

  public String getTemplateColour() {
    return templateColour;
  }

  public void setTemplateColour(String templateColour) {
    this.templateColour = templateColour;
  }

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
