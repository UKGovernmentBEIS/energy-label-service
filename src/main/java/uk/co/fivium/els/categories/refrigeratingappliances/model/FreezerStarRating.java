package uk.co.fivium.els.categories.refrigeratingappliances.model;

public enum FreezerStarRating {
  ZERO_STAR("Zero star", "starRating0"),
  ONE_STAR("One star", "starRating1"),
  TWO_STAR("Two star", "starRating2"),
  THREE_STAR("Three star", "starRating3"),
  FOUR_STAR("Four star", "starRating4");

  private final String displayValue;
  private final String templateStarRatingClassName;

  FreezerStarRating(String displayValue, String templateStarRatingClassName) {
    this.displayValue = displayValue;
    this.templateStarRatingClassName = templateStarRatingClassName;
  }

  public String getDisplayValue() {
    return displayValue;
  }

  public String getTemplateStarRatingClassName() {
    return templateStarRatingClassName;
  }
}
