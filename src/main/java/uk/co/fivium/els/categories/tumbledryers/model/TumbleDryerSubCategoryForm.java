package uk.co.fivium.els.categories.tumbledryers.model;

import javax.validation.constraints.NotBlank;

public class TumbleDryerSubCategoryForm {

  @NotBlank(message = "Select a type of tumble dryer")
  private String subCategory;

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }
}