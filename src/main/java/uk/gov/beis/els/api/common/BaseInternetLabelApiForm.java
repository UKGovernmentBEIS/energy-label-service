package uk.gov.beis.els.api.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class BaseInternetLabelApiForm {

  @FieldPrompt(value = "Enter height of the product's price (in pixels)")
  @NotNull
  @Digits(integer = 3, fraction = 0, message = "Enter the height of the product price, up to 3 digits long")
  @Min(value = 1, message = "Height of product's price must be greater than zero")
  @Schema(type = "integer")
  private int productPriceHeightPx;

  @FieldPrompt("Arrow direction")
  @NotNull
  private InternetLabelOrientation labelOrientation;

  @FieldPrompt("Image format")
  @NotNull
  private InternetLabelFormat labelFormat;

  public int getProductPriceHeightPx() {
    return productPriceHeightPx;
  }

  public void setProductPriceHeightPx(int productPriceHeightPx) {
    this.productPriceHeightPx = productPriceHeightPx;
  }

  public InternetLabelOrientation getLabelOrientation() {
    return labelOrientation;
  }

  public void setLabelOrientation(InternetLabelOrientation labelOrientation) {
    this.labelOrientation = labelOrientation;
  }

  public InternetLabelFormat getLabelFormat() {
    return labelFormat;
  }

  public void setLabelFormat(InternetLabelFormat labelFormat) {
    this.labelFormat = labelFormat;
  }

}
