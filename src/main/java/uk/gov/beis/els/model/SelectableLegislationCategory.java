package uk.gov.beis.els.model;

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

  public static SelectableLegislationCategory preMarch2021(RatingClassRange primaryRatingRange) {
    return new SelectableLegislationCategory("PRE_MAR2021", "An old-style energy label", primaryRatingRange, null);
  }

  public static SelectableLegislationCategory preMarch2021(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    return new SelectableLegislationCategory("PRE_MAR2021", "An old-style energy label", primaryRatingRange, secondaryRatingRange);
  }

  public static SelectableLegislationCategory postMarch2021() {
    return new SelectableLegislationCategory("POST_MAR2021", "A new-style rescaled energy label", RatingClassRange.of(RatingClass.A, RatingClass.G), null, InternetLabelTemplate.RESCALED);
  }

  public static SelectableLegislationCategory postMarch2021(RatingClassRange secondaryRatingRange) {
    return new SelectableLegislationCategory("POST_MAR2021", "A new-style rescaled energy label", RatingClassRange.of(RatingClass.A, RatingClass.G), secondaryRatingRange, InternetLabelTemplate.RESCALED);
  }

  public static SelectableLegislationCategory preSeptember2021(RatingClassRange primaryRatingRange) {
    return new SelectableLegislationCategory("PRE_SEPT2021", "An original-style label for display before 1 September 2021", primaryRatingRange, null);
  }

  public static SelectableLegislationCategory postSeptember2021() {
    return new SelectableLegislationCategory("POST_SEPT2021", "A new-style 'rescaled' label for display after 1 September 2021", RatingClassRange.of(RatingClass.A, RatingClass.G), null, InternetLabelTemplate.RESCALED);
  }

  public SelectableLegislationCategory(String id, String displayName, RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    super(primaryRatingRange, secondaryRatingRange, InternetLabelTemplate.ORIGINAL);
    this.id = id;
    this.displayName = displayName;
  }

  public SelectableLegislationCategory(String id, String displayName, RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    super(primaryRatingRange, secondaryRatingRange, internetLabelTemplate);
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
