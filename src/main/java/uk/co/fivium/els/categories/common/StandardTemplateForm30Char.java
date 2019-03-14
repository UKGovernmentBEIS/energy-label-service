package uk.co.fivium.els.categories.common;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import uk.co.fivium.els.model.FieldPrompt;

/**
 * Standard form elements for a large templates allowing 30 char length supplier name/model fields
 */
public class StandardTemplateForm30Char {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark")
  @Length(max = 30, message = "Supplier name or trade mark must be 30 characters or less")
  private String supplierName;

  @FieldPrompt("Supplier's model identifier")
  @NotBlank(message = "Enter a supplier model identifier")
  @Length(max = 30, message = "Supplier model identifier must be 30 characters or less")
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
