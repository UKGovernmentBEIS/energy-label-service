package uk.gov.beis.els.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The page could not be found")
public class TagNotFoundException extends RuntimeException {

  public TagNotFoundException(String message) {
    super(message);
  }
}
