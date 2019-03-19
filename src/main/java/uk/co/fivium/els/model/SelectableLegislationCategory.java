package uk.co.fivium.els.model;

import java.util.List;

public class SelectableLegislationCategory extends LegislationCategory {

  private final String id;
  private final String displayName;

  public static SelectableLegislationCategory getById(String id, List<SelectableLegislationCategory> legislationCategories) {
    return legislationCategories.stream()
        .filter(l -> l.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Legislation category with id '%s' not found", id)));
  }

  public static SelectableLegislationCategory of(String id, String displayName, RatingClassRange primaryRatingRange) {
    return new SelectableLegislationCategory(id, displayName, primaryRatingRange, null);
  }

  public static SelectableLegislationCategory of(String id, String displayName, RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    return new SelectableLegislationCategory(id, displayName, primaryRatingRange, secondaryRatingRange);
  }

  public SelectableLegislationCategory(String id, String displayName, RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    super(primaryRatingRange, secondaryRatingRange);
    this.id = id;
    this.displayName = displayName;
  }

  public String getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }
}
