package uk.gov.beis.els.model;

public enum EnergyLabelFormat {
  PDF(".pdf"),
  PNG(".png"),
  JPEG(".jpeg");

  private final String fileExtension;

  EnergyLabelFormat(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public String getFileExtension() {
    return fileExtension;
  }
}
