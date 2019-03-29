package uk.gov.beis.els.model;

public class LegislationCategory {

  private final RatingClassRange primaryRatingRange;
  private final RatingClassRange secondaryRatingRange;

  public static Boolean isPrimaryRatingClassValid(String ratingClass, LegislationCategory legislationCategory) {
    RatingClass rating = RatingClass.valueOf(ratingClass);
    return legislationCategory.getPrimaryRatingRange().getApplicableRatings().contains(rating);
  }

  public static Boolean isSecondaryRatingClassValid(String ratingClass, LegislationCategory legislationCategory) {
    RatingClass rating = RatingClass.valueOf(ratingClass);
    return legislationCategory.getSecondaryRatingRange().getApplicableRatings().contains(rating);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange) {
    return new LegislationCategory(primaryRatingRange, null);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    return new LegislationCategory( primaryRatingRange, secondaryRatingRange);
  }

  public LegislationCategory(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    this.primaryRatingRange = primaryRatingRange;
    this.secondaryRatingRange = secondaryRatingRange;
  }

  public RatingClassRange getPrimaryRatingRange() {
    return primaryRatingRange;
  }

  public RatingClassRange getSecondaryRatingRange() {
    return secondaryRatingRange;
  }

}
