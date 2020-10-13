package uk.gov.beis.els.model;

import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;

public class LegislationCategory {

  private final RatingClassRange primaryRatingRange;
  private final RatingClassRange secondaryRatingRange;
  private final InternetLabelTemplate internetLabelTemplate;

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

  public static LegislationCategory of(RatingClassRange primaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    return new LegislationCategory(primaryRatingRange, null, internetLabelTemplate);
  }

  public static LegislationCategory of(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    return new LegislationCategory( primaryRatingRange, secondaryRatingRange, internetLabelTemplate);
  }

  public LegislationCategory(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange) {
    this.primaryRatingRange = primaryRatingRange;
    this.secondaryRatingRange = secondaryRatingRange;
    this.internetLabelTemplate = InternetLabelTemplate.ORIGINAL;
  }

  public LegislationCategory(RatingClassRange primaryRatingRange, RatingClassRange secondaryRatingRange, InternetLabelTemplate internetLabelTemplate) {
    this.primaryRatingRange = primaryRatingRange;
    this.secondaryRatingRange = secondaryRatingRange;
    this.internetLabelTemplate = internetLabelTemplate;
  }

  public RatingClassRange getPrimaryRatingRange() {
    return primaryRatingRange;
  }

  public RatingClassRange getSecondaryRatingRange() {
    return secondaryRatingRange;
  }

  public InternetLabelTemplate getInternetLabelTemplate() { return internetLabelTemplate; }
}
