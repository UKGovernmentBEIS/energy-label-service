package uk.co.fivium.els.categories.internetlabelling.model;

public enum InternetLabelOrientation {
  LEFT("Pointing left"),
  RIGHT("Pointing right");

  private final String displayName;

  InternetLabelOrientation(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
