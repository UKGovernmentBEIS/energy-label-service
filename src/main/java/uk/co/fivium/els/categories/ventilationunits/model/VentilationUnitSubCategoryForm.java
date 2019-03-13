package uk.co.fivium.els.categories.ventilationunits.model;

import javax.validation.constraints.NotBlank;

public class VentilationUnitSubCategoryForm {

  @NotBlank
  private String subCategory;

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }
}