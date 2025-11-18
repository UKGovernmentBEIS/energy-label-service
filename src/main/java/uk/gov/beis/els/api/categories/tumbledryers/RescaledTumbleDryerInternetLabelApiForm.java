package uk.gov.beis.els.api.categories.tumbledryers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.categories.tumbledryers.service.RescaledTumbleDryersService;
import uk.gov.beis.els.model.meta.FieldPrompt;

// This can't extend BaseInternetLabelApiForm as rescaled tumble dryers do not allow the label orientation to be specified
public class RescaledTumbleDryerInternetLabelApiForm {
  @Schema(description = "The energy efficiency class indicator")
  @NotNull
  @ApiValuesFromLegislationCategory(serviceClass = RescaledTumbleDryersService.class)
  private String efficiencyRating;
  
  @FieldPrompt(value = "Enter height of the product's price (in pixels)")
  @Digits(integer = 3, fraction = 0, message = "Enter the height of the product price, up to 3 digits long")
  @Min(value = 1, message = "Height of product's price must be greater than zero")
  @NotNull
  @Schema(type = "integer")
  private int productPriceHeightPx;

  @Schema(description = "The colour of the arrow image. Use a colour arrow if you can. You can use a black and white arrow if your material is being printed in black and white. You shouldn't use black and white arrows on the internet.")
  @NotNull
  @ApiValuesFromEnum(value = InternetLabelColour.class)
  private String labelColour;

  @FieldPrompt("Image format")
  @NotNull
  @ApiValuesFromEnum(value = InternetLabelFormat.class)
  private String labelFormat;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
  
  public int getProductPriceHeightPx() {
    return productPriceHeightPx;
  }

  public void setProductPriceHeightPx(int productPriceHeightPx) {
    this.productPriceHeightPx = productPriceHeightPx;
  }

  public String getLabelColour() {
    return labelColour;
  }

  public void setLabelColour(String labelColour) {
    this.labelColour = labelColour;
  }

  public String getLabelFormat() {
    return labelFormat;
  }

  public void setLabelFormat(String labelFormat) {
    this.labelFormat = labelFormat;
  }
}
