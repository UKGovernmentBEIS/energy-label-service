package uk.gov.beis.els.api.common;


import java.time.Instant;
import java.util.List;

/**
 * Error response object matching the fields from DefaultErrorAttributes, but with additional list of validation errors.
 * The default BindingResult errors enabled with server.error.include-binding-errors=always are not used as they include
 * a lot of extraneous data.
 */
public class ApiErrorResponse {
  public final Instant timestamp;
  public final int status;
  public final String error;
  public final String message;
  public final List<ApiFieldError> validationErrors;
  public final String path;

  public ApiErrorResponse(Instant timestamp, int status, String error, String message, List<ApiFieldError> validationErrors, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.validationErrors = validationErrors;
    this.path = path;
  }
}
