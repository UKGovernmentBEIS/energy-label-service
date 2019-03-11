package uk.co.fivium.elp.categories.lamps.model;

public enum TemplateType {
  COLOUR("Colour"),
  BW("Black and white");

  private final String displayName;

  TemplateType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
