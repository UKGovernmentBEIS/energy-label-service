package uk.co.fivium.els.categories.common;

import java.util.List;

public interface Category {

  String getCategoryQuestionText();
  String getNoSelectionErrorMessage();
  String getGuidanceText();
  List<CategoryItem> getCategoryItems();

  default CategoryItem getCategoryItem(String id) {
    return getCategoryItems().stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Cannot find category item with id '%s'", id)));
  }
}
