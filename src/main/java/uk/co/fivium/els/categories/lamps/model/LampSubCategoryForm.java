package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.NotBlank;

public class LampSubCategoryForm {

  @NotBlank(message = "Select a type of label")
  private String subCategory;

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }
}