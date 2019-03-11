package uk.co.fivium.elp.categories.lamps.model;

import javax.validation.constraints.NotBlank;

public class LampSubCategoryForm {

  @NotBlank
  private String subCategory;

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }
}