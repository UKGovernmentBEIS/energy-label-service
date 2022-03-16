package uk.gov.beis.els.util;

public class StringUtils {

  private static final int DECIMAL_PLACES_DEFAULT = 0;

  public static String toPercentage(float number) {
    return toPercentage(number, DECIMAL_PLACES_DEFAULT);
  }

  public static String toPercentage(float number, int decimalPlaces) {
    return String.format("%." + decimalPlaces + "f", number * 100);
  }
}