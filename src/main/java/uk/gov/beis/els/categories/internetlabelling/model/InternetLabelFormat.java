package uk.gov.beis.els.categories.internetlabelling.model;

public enum InternetLabelFormat {
  JPEG(".jpg", "JPG"),
  PNG(".png", "PNG");

  private final String fileExtension;
  private final String displayName;

  InternetLabelFormat(String fileExtension, String displayName) {
    this.fileExtension = fileExtension;
    this.displayName = displayName;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public String getDisplayName() {
    return displayName;
  }
}
