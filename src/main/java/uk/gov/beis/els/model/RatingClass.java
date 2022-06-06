package uk.gov.beis.els.model;

import java.util.Arrays;

public enum RatingClass {
  APPPP("A++++", "A", "++++"),
  APPP("A+++", "A", "+++"),
  APP("A++", "A", "++"),
  AP("A+", "A", "+"),
  A("A", "A", ""),
  B("B", "B", ""),
  C("C", "C", ""),
  D("D", "D", ""),
  E("E", "E", ""),
  F("F", "F", ""),
  G("G", "G", "");

  private final String displayValue;
  private final String letter;
  private final String plusses;

  RatingClass(String displayValue, String letter, String plusses) {
    this.displayValue = displayValue;
    this.letter = letter;
    this.plusses = plusses;
  }

  public String getDisplayValue() {
    return displayValue;
  }

  public String getLetter() {
    return letter;
  }

  public String getPlusses() {
    return plusses;
  }

  public static RatingClass getEnum(String ratingClass) {
    return Arrays.stream(RatingClass.values())
        .filter(e -> e.getDisplayValue().equals(ratingClass) || e.name().equals(ratingClass))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Can't resolve RatingClass from string %s", ratingClass)));
  }

}
