package uk.gov.beis.els.categories.internetlabelling.model;

public enum InternetLabelColour {
  COLOUR("Colour"),
  BLACK_AND_WHITE("Black and white");

  private final String displayName;

  InternetLabelColour(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
