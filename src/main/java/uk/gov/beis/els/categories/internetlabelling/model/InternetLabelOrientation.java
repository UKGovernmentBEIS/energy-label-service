package uk.gov.beis.els.categories.internetlabelling.model;

public enum InternetLabelOrientation {
  LEFT("Pointing left <svg class=\"arrow-direction-label-icon\" aria-hidden=\"true\" focusable=\"false\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 50 20\" height=\"20\" width=\"50\"><path fill=\"currentColor\" d=\"M10 0 50 0 50 20 10 20 0 10 10 0\"/></svg>", "Left"),
  RIGHT("Pointing right <svg class=\"arrow-direction-label-icon\" aria-hidden=\"true\" focusable=\"false\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 50 20\" height=\"20\" width=\"50\"><path fill=\"currentColor\" d=\"M0 0 40 0 50 10 40 20 0 20 0 0\"/></svg>", "Right");

  private final String displayName;
  private final String shortName;

  InternetLabelOrientation(String displayName, String shortName) {
    this.displayName = displayName;
    this.shortName = shortName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getShortName() {
    return shortName;
  }
}
