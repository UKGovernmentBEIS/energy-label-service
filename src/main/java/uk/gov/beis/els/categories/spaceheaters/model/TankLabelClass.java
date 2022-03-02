package uk.gov.beis.els.categories.spaceheaters.model;

import uk.gov.beis.els.model.Displayable;

public enum TankLabelClass implements Displayable {
  AP("A+", "A", "+", 0.95F),
  A("A", "A", "", 0.91F),
  B("B", "B", "", 0.86F),
  C("C", "C", "", 0.83F),
  D("D", "D", "", 0.81F),
  E("E", "E", "", 0.81F),
  F("F", "F", "", 0.81F),
  G("G", "G", "", 0.81F);

  private final String displayName;
  private final String letter;
  private final String plusses;
  private final float ratingValue;

  TankLabelClass(String displayName, String letter, String plusses, float ratingValue) {
    this.displayName = displayName;
    this.letter = letter;
    this.plusses = plusses;
    this.ratingValue = ratingValue;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  public String getLetter() {
    return letter;
  }

  public String getPlusses() {
    return plusses;
  }

  public float getRatingValue() {
    return ratingValue;
  }
}
