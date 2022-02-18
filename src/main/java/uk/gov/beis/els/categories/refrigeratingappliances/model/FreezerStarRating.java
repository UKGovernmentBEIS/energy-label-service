package uk.gov.beis.els.categories.refrigeratingappliances.model;

public enum FreezerStarRating {
  ONE_STAR("One star", "starRating1"),
  TWO_STAR("Two star", "starRating2"),
  THREE_STAR("Three star", "starRating3"),
  FOUR_STAR("Four star", "starRating4");

  private final String displayName;
  private final String templateStarRatingClassName;

  FreezerStarRating(String displayName, String templateStarRatingClassName) {
    this.displayName = displayName;
    this.templateStarRatingClassName = templateStarRatingClassName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getTemplateStarRatingClassName() {
    return templateStarRatingClassName;
  }
}
