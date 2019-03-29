package uk.gov.beis.els.model;

public enum GoogleAnalyticsEventCategory {
  ENERGY_LABEL("Energy label"),
  INTERNET_LABEL("Internet arrow");

  private final String displayValue;

  GoogleAnalyticsEventCategory(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
