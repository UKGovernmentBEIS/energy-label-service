package uk.gov.beis.els.categories.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.model.meta.FieldPrompt;

/**
 * Standard form elements for a large templates allowing 30 char length supplier name/model fields
 */
public class StandardTemplateForm30Char extends InternetLabellingForm implements SupplierNameForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark")
  @Length(max = 30, message = "Supplier name or trade mark must be 30 characters or less")
  @Schema(example = "Acme Inc")
  private String supplierName;

  @FieldPrompt("Supplier's model identification code")
  @NotBlank(message = "Enter a supplier model identification code")
  @Length(max = 30, message = "Supplier model identification code must be 30 characters or less")
  @Schema(example = "Test123")
  private String modelName;

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }
}
