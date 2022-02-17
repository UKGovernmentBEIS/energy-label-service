package uk.gov.beis.els.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

  private static final Map<String, RatingClass> ENUM_MAP;
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

  static {
    Map<String, RatingClass> map = new HashMap<String, RatingClass>();
    for (RatingClass instance : RatingClass.values()) {
      map.put(instance.getDisplayValue().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static RatingClass get(String name) {
    return ENUM_MAP.get(name.toLowerCase());
  }
}
