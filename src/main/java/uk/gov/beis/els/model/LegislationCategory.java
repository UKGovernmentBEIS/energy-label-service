package uk.gov.beis.els.model;

public class LegislationCategory {

  private final RatingClassRange primaryRatingRange;
  private final RatingClassRange secondaryRatingRange;
  private final RatingClassRange tertiaryRatingRange;
  private final InternetLabelTemplate internetLabelTemplate;

  public static Boolean isPrimaryRatingClassValid(String ratingClass, LegislationCategory legislationCategory) {
    RatingClass rating = RatingClass.getEnum(ratingClass);
    return legislationCategory.getPrimaryRatingRange().getApplicableRatings().contains(rating);
  }

  public static Boolean isSecondaryRatingClassValid(String ratingClass, LegislationCategory legislationCategory) {
    RatingClass rating = RatingClass.getEnum(ratingClass);
    return legislationCategory.getSecondaryRatingRange().getApplicableRatings().contains(rating);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange) {
    return new LegislationCategory(primaryRatingRange, null, null, InternetLabelTemplate.ORIGINAL);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    return new LegislationCategory( primaryRatingRange, secondaryRatingRange, null, InternetLabelTemplate.ORIGINAL);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    return new LegislationCategory(primaryRatingRange, null, null, internetLabelTemplate);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    return new LegislationCategory( primaryRatingRange, secondaryRatingRange, null, internetLabelTemplate);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, RatingClassRange tertiaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    return new LegislationCategory( primaryRatingRange, secondaryRatingRange, tertiaryRatingRange, internetLabelTemplate);
  }

  public LegislationCategory(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, RatingClassRange tertiaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    this.primaryRatingRange = primaryRatingRange;
    this.secondaryRatingRange = secondaryRatingRange;
    this.tertiaryRatingRange = tertiaryRatingRange;
    this.internetLabelTemplate = internetLabelTemplate;
  }

  public RatingClassRange getPrimaryRatingRange() {
    return primaryRatingRange;
  }

  public RatingClassRange getSecondaryRatingRange() {
    return secondaryRatingRange;
  }
  
  public RatingClassRange getTertiaryRatingRange() {
    return tertiaryRatingRange;
  }

  public InternetLabelTemplate getInternetLabelTemplate() { return internetLabelTemplate; }
}
