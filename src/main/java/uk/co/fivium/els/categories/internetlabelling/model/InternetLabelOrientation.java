package uk.co.fivium.els.categories.internetlabelling.model;

public enum InternetLabelOrientation {
  LEFT("Pointing left <svg style=\"color: #00A64F; vertical-align: -4px; margin-left: 5px;\" role=\"presentation\" focusable=\"false\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 50 20\" height=\"20\" width=\"50\"><path fill=\"currentColor\" d=\"M10 0 50 0 50 20 10 20 0 10 10 0\"/></svg>"),
  RIGHT("Pointing right <svg style=\"color: #00A64F; vertical-align: -4px; margin-left: 5px;\" role=\"presentation\" focusable=\"false\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 50 20\" height=\"20\" width=\"50\"><path fill=\"currentColor\" d=\"M0 0 40 0 50 10 40 20 0 20 0 0\"/></svg>");

  private final String displayName;

  InternetLabelOrientation(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
