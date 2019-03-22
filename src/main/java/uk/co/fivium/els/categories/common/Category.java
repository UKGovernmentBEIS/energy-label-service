package uk.co.fivium.els.categories.common;

import java.util.List;

public interface Category {

  String getCategoryQuestionText();
  String getNoSelectionErrorMessage();
  String getCommonProductGuidanceText();
  List<CategoryItem> getCategoryItems();

  default CategoryItem getCategoryItem(String id) {
    return getCategoryItems().stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Cannot find category item with id '%s'", id)));
  }

  // Currently only Lamps have help text on the category page
  default String getCategoryPageGuidanceText() {
    return null;
  }
}
