package uk.co.fivium.els.categories.common;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import uk.co.fivium.els.model.FieldPrompt;

/**
 * Standard form elements for Lamp templates which only allow 20 chars for supplier name/model fields
 */
public class StandardTemplateForm20Char {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark")
  @Length(max = 20, message = "Supplier name or trade mark must be 20 characters or less")
  private String supplierName;

  @FieldPrompt("Supplier's model identifier")
  @NotBlank(message = "Enter a supplier model identifier")
  @Length(max = 20, message = "Supplier model identifier must be 20 characters or less")
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
