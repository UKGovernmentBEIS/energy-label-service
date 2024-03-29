package uk.gov.beis.els.api.common;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("uk.gov.beis.els.api")
public class ApiControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<ApiErrorResponse> handleApiValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<ApiFieldError> apiFieldErrorList = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(e -> new ApiFieldError(e.getField(), e.getDefaultMessage()))
        .collect(Collectors.toList());

    ApiErrorResponse response = new ApiErrorResponse(
        Instant.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad request",
        "There were validation errors",
        apiFieldErrorList,
        request.getRequestURI()
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
