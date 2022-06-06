package uk.gov.beis.els.categories.lamps.model;

public enum TemplateColour {
  COLOUR("Colour"),
  BLACK_AND_WHITE("Black and white");

  private final String displayName;

  TemplateColour(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
