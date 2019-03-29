package uk.gov.beis.els.categories.internetlabelling.model;

public enum InternetLabelFormat {
  JPEG(".jpg"),
  PNG(".png");

  private final String fileExtension;

  InternetLabelFormat(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public String getFileExtension() {
    return fileExtension;
  }
}
