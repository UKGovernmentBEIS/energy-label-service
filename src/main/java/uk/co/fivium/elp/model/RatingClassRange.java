package uk.co.fivium.elp.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class RatingClassRange {

  private RatingClass highestRating;
  private RatingClass lowestRating;

  public static RatingClassRange of(RatingClass highestRating, RatingClass lowestRating) {
    return new RatingClassRange(highestRating, lowestRating);
  }

  public RatingClassRange(RatingClass highestRating, RatingClass lowestRating) {
    this.highestRating = highestRating;
    this.lowestRating = lowestRating;
  }

  public RatingClass getHighestRating() {
    return highestRating;
  }

  public RatingClass getLowestRating() {
    return lowestRating;
  }

  public List<RatingClass> getApplicableRatings() {
    return new ArrayList<>(EnumSet.range(highestRating, lowestRating));
  }
}
