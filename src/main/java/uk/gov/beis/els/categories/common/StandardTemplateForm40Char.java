package uk.gov.beis.els.categories.common;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.model.meta.FieldPrompt;

/**
 * Standard form elements for a large templates allowing 50 char length supplier name/model fields
 */
public class StandardTemplateForm40Char extends InternetLabellingForm implements SupplierNameForm {

  @FieldPrompt("Supplier's name or trade mark")
  @NotBlank(message = "Enter a supplier name or trade mark")
  @Length(max = 40, message = "Supplier name or trade mark must be 40 characters or less")
  private String supplierName;

  @FieldPrompt("Supplier's model identification code")
  @NotBlank(message = "Enter a supplier model identification code")
  @Length(max = 40, message = "Supplier model identification code must be 40 characters or less")
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
