package uk.co.fivium.elp.model;

public enum ProductCategory {

  AIR_CONDITIONERS("Air Conditioners", "reverse-route-here");

  private final String displayName;
  private final String nextSateUrl;

  ProductCategory(String displayName, String nextSateUrl) {
    this.displayName = displayName;
    this.nextSateUrl = nextSateUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getNextSateUrl() {
    return nextSateUrl;
  }
}
