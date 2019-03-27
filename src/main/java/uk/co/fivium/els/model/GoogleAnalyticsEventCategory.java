package uk.co.fivium.els.model;

public enum GoogleAnalyticsEventCategory {
  ENERGY_LABEL("Energy label"),
  INTERNET_LABEL("Internet label");

  private final String displayValue;

  GoogleAnalyticsEventCategory(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
