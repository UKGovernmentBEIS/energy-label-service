package uk.co.fivium.els.model;

public enum ProductMetadata {

  LAMPS_FULL("Lamps - Label with supplier's name, model identification code, rating and energy consumption"),
  LAMPS_RATING_CONSUMPTION("Lamps - Label with energy rating and weighted energy consumption only"),
  LAMPS_RATING("Lamps - Label with energy rating only");

  private final String analyticsLabel;

  ProductMetadata(String analyticsLabel) {
    this.analyticsLabel = analyticsLabel;
  }

  public String getAnalyticsLabel() {
    return analyticsLabel;
  }

  // Use the analytics label for now, unless we want to keep the filenames shorter.
  public String getProductFileName() {
    return analyticsLabel;
  }
}
