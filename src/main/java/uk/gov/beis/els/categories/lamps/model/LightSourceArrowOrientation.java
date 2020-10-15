package uk.gov.beis.els.categories.lamps.model;

public enum LightSourceArrowOrientation {
  LEFT("Pointing left <svg class=\"arrow-direction-label-icon arrow-direction-label-icon--lamps-packaging\" role=\"presentation\" focusable=\"false\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 30 20\" height=\"20\" width=\"30\"><path fill=\"currentColor\" stroke=\"black\" stroke-width=\"1\" stroke-linejoin=\"round\" d=\"M9 1 29 1 29 19 9 19 1 10Z\"/></svg>", "Left"),
  RIGHT("Pointing right <svg class=\"arrow-direction-label-icon arrow-direction-label-icon--lamps-packaging\" role=\"presentation\" focusable=\"false\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 30 20\" height=\"20\" width=\"30\"><path fill=\"currentColor\" stroke=\"black\" stroke-width=\"1\" stroke-linejoin=\"round\" d=\"M1 1 21 1 29 10 21 19 1 19Z\"/></svg>", "Right");

  private final String displayName;
  private final String shortName;

  LightSourceArrowOrientation(String displayName, String shortName) {
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
