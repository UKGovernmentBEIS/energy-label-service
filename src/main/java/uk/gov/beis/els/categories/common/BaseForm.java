package uk.gov.beis.els.categories.common;

import javax.validation.constraints.Pattern;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.model.meta.FieldPrompt;

/**
 * A base form containing common fields which all specific forms inherit from.
 */
public class BaseForm extends InternetLabellingForm {

  @FieldPrompt(value = "Link to the EPREL or other website which provides further energy efficiency information about this product",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the EPREL or other website. Links must start with http:// or https:// and contain at least one dot (.) character",
      groups = {PostMarch2021Field.class, PostSeptember2021Field.class})
  private String qrCodeUrl;

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
