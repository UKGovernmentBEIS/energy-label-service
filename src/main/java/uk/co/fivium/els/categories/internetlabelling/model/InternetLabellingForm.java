package uk.co.fivium.els.categories.internetlabelling.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.categories.common.AnalyticsForm;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.InternetLabelModeField;

public class InternetLabellingForm extends AnalyticsForm {

  @FieldPrompt("Enter height of the product price (in pixels)")
  @Digits(integer = 3, fraction = 0, groups = InternetLabellingGroup.class, message = "Enter the height of the product price, up to 3 digits long")
  @InternetLabelModeField
  private String productPriceHeightPx;

  @FieldPrompt("Arrow direction")
  @NotBlank(groups = InternetLabellingGroup.class, message = "Select an arrow direction")
  @InternetLabelModeField
  private String labelOrientation;

  @FieldPrompt("Image format")
  @NotBlank(groups = InternetLabellingGroup.class, message = "Select an image format")
  @InternetLabelModeField
  private String labelFormat;

  public String getProductPriceHeightPx() {
    return productPriceHeightPx;
  }

  public void setProductPriceHeightPx(String productPriceHeightPx) {
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
