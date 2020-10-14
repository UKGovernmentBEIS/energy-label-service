package uk.gov.beis.els.categories.internetlabelling.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import uk.gov.beis.els.categories.common.AnalyticsForm;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.InternetLabelModeField;

public class InternetLabellingForm extends AnalyticsForm {

  @FieldPrompt(value = "Enter height of the product's price (in pixels)", hintText = "You might need to ask your web or marketing team for this")
  @Digits(integer = 3, fraction = 0, groups = InternetLabellingGroup.class, message = "Enter the height of the product price, up to 3 digits long")
  @InternetLabelModeField
  private String productPriceHeightPx;

  @FieldPrompt(value = "Should the arrow be in colour or black and white?", hintText = "Use a colour arrow if you can. You can use a black and white arrow if your material is being printed in black and white. You shouldn't use black and white arrows on the internet.")
  @NotBlank(groups = RescaledInternetLabellingGroup.class, message = "Select whether the arrow should be in colour or black and white")
  @InternetLabelModeField
  private String labelColour;

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

  public String getLabelColour() {
    return labelColour;
  }

  public void setLabelColour(String labelColour) {
    this.labelColour = labelColour;
  }
}
