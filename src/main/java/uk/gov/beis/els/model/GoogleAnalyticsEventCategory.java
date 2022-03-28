package uk.gov.beis.els.model;

public enum GoogleAnalyticsEventCategory {
  ENERGY_LABEL("Energy label"),
  ENERGY_LABEL_API("Energy label - API"),
  FICHE("Fiche"),
  FICHE_API("Fiche - API"),
  INTERNET_LABEL("Internet arrow"),
  INTERNET_LABEL_API("Internet arrow - API");

  private final String displayValue;

  GoogleAnalyticsEventCategory(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
