package uk.co.fivium.els.categories.waterheaters.model;

public enum LoadProfile {
  XXXS("3XS"),
  XXS("XXS"),
  XS("XS"),
  S("S"),
  M("M"),
  L("L"),
  XL("XL"),
  XXL("XXL");

  private final String displayName;

  LoadProfile(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
