package uk.co.fivium.elp.model;

public class LegislationCategory {

  private final RatingClassRange primaryRatingRange;
  private final RatingClassRange secondaryRatingRange;
  private final String templatePath;

  public static LegislationCategory of(RatingClassRange primaryRatingRange, String templatePath) {
    return new LegislationCategory(primaryRatingRange, null, templatePath);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, String templatePath) {
    return new LegislationCategory(primaryRatingRange, secondaryRatingRange, templatePath);
  }

  public LegislationCategory(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, String templatePath) {
    this.primaryRatingRange = primaryRatingRange;
    this.secondaryRatingRange = secondaryRatingRange;
    this.templatePath = templatePath;
  }

  public RatingClassRange getPrimaryRatingRange() {
    return primaryRatingRange;
  }

  public RatingClassRange getSecondaryRatingRange() {
    return secondaryRatingRange;
  }

  public String getTemplatePath() {
    return templatePath;
  }
}
