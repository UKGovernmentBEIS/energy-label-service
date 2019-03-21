package uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.model;

public enum ClimateClass {
  THREE("3", "climateClass3"),
  FOUR("4", "climateClass4"),
  FIVE("5", "climateClass5");

  private final String displayName;
  private final String svgClass;

  ClimateClass(String displayName, String svgClass) {
    this.displayName = displayName;
    this.svgClass = svgClass;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getSvgClass() {
    return svgClass;
  }
}
