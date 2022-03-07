package uk.gov.beis.els.api.common;

public class ApiFieldError {

  private final String propertyName;
  private final String errorMessage;

  public ApiFieldError(String propertyName, String errorMessage) {
    this.propertyName = propertyName;
    this.errorMessage = errorMessage;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
