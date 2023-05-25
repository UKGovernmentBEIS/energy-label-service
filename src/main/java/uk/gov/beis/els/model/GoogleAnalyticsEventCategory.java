package uk.gov.beis.els.model;

public enum GoogleAnalyticsEventCategory {
  // NB - displayValue must be 40 characters or fewer, or GA will reject it
  // https://developers.google.com/analytics/devguides/collection/protocol/ga4/sending-events?client_type=gtag#limitations
  ENERGY_LABEL("label_download"),
  ENERGY_LABEL_API("label_download_api"),
  FICHE("fiche_download"),
  FICHE_API("fiche_download_api"),
  INTERNET_LABEL("arrow_download"),
  INTERNET_LABEL_API("arrow_download_api");

  private final String displayValue;

  GoogleAnalyticsEventCategory(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
