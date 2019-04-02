package uk.gov.beis.els.model;

public enum GoogleAnalyticsEventAction {
  DOWNLOAD("Download");

  private final String displayValue;

  GoogleAnalyticsEventAction(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}