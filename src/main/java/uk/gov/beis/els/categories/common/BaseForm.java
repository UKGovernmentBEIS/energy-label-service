package uk.gov.beis.els.categories.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Pattern;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.model.meta.FieldPrompt;

/**
 * A base form containing common fields which all specific forms inherit from.
 */
public class BaseForm extends InternetLabellingForm {

  @FieldPrompt(value = "Link to the product information sheet for this product on a publicly accessible website",
      hintText = "This link will be shown as a QR code on the label. Links should be under 300 characters to make sure they can be scanned reliably.")
  @Pattern(regexp = "^(https|http)://([a-zA-Z0-9\\-]+)\\.[a-zA-Z0-9]+.*",
      message = "Enter a link to the product information sheet. Links must start with http:// or https:// and contain at least one dot (.) character",
      groups = {PostMarch2021Field.class, PostSeptember2021Field.class})
  @Schema(hidden = true)
  private String qrCodeUrl;

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }
}
