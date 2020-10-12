package uk.gov.beis.els.categories.common;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import uk.gov.beis.els.model.meta.FieldPrompt;

/**
 * Standard form elements for templates which only allow 20 chars for supplier name/model fields
 */
public class StandardTemplateForm20Char extends BaseForm implements SupplierNameForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark")
  @Length(max = 20, message = "Supplier name or trade mark must be 20 characters or less")
  private String supplierName;

  @FieldPrompt("Supplier's model identification code")
  @NotBlank(message = "Enter a supplier model identification code")
  @Length(max = 20, message = "Supplier model identification code must be 20 characters or less")
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
