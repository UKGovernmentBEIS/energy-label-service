package uk.gov.beis.els.categories.internetlabelling.model;

public enum InternetLabelColour {
  COLOUR("Colour"),
  BW("Black and white");

  private final String displayName;

  InternetLabelColour(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
