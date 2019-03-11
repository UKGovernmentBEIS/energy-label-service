package uk.co.fivium.elp.model;

public enum RatingClass {
  APPPP("A++++", "A", "++++", "ratingAPPPP"),
  APPP("A+++", "A", "+++", "ratingAPPP"),
  APP("A++", "A", "++", "ratingAPP"),
  AP("A+", "A", "+", "ratingAP"),
  A("A", "A", "", "ratingA"),
  B("B", "B", "", "ratingB"),
  C("C", "C", "", "ratingC"),
  D("D", "D", "", "ratingD"),
  E("E", "E", "", "ratingE"),
  F("F", "F", "", "ratingF"),
  G("G", "G", "", "ratingG");

  private final String displayValue;
  private final String letter;
  private final String plusses;
  private final String templateRatingClassName;

  RatingClass(String displayValue, String letter, String plusses, String templateRatingClassName) {
    this.displayValue = displayValue;
    this.letter = letter;
    this.plusses = plusses;
    this.templateRatingClassName = templateRatingClassName;
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

  public String getTemplateRatingClassName() {
    return templateRatingClassName;
  }
}
