package uk.co.fivium.els.categories.waterheaters.model;

import javax.validation.constraints.NotBlank;

public class WaterHeaterSubCategoryForm {

  @NotBlank(message = "Select a type of water heater or storage tank")
  private String subCategory;

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }
}