package uk.gov.beis.els.api.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class BaseInternetLabelApiForm {

  @FieldPrompt(value = "Enter height of the product's price (in pixels)")
  @Digits(integer = 3, fraction = 0, message = "Enter the height of the product price, up to 3 digits long")
  @Min(value = 1, message = "Height of product's price must be greater than zero")
  @NotNull
  @Schema(type = "integer")
  private int productPriceHeightPx;

  @FieldPrompt("Arrow direction")
  @NotNull
  @ApiValuesFromEnum(value = InternetLabelOrientation.class)
  private String labelOrientation;

  @FieldPrompt("Image format")
  @NotNull
  @ApiValuesFromEnum(value = InternetLabelFormat.class)
  private String labelFormat;

  public int getProductPriceHeightPx() {
    return productPriceHeightPx;
  }

  public void setProductPriceHeightPx(int productPriceHeightPx) {
    this.productPriceHeightPx = productPriceHeightPx;
  }

  public String getLabelOrientation() {
    return labelOrientation;
  }

  public void setLabelOrientation(String labelOrientation) {
    this.labelOrientation = labelOrientation;
  }

  public String getLabelFormat() {
    return labelFormat;
  }

  public void setLabelFormat(String labelFormat) {
    this.labelFormat = labelFormat;
  }

}
