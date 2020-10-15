package uk.gov.beis.els.categories.lamps.model;

public enum TemplateSize {
  STANDARD("Standard (36mm x 72mm)"),
  SMALL("Small (20mm x 54mm)");

  private final String displayName;

  TemplateSize(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
