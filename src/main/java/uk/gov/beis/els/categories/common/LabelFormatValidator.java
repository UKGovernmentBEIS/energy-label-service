package uk.gov.beis.els.categories.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class LabelFormatValidator implements ConstraintValidator<LabelFormatConstraint, String> {
  private static final Logger LOGGER = LoggerFactory.getLogger(LabelFormatValidator.class);

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (isApiRequest()) {
      return true; // label format optional for API requests
    }

    return value != null && !value.isEmpty();
  }

  private boolean isApiRequest() {
    try {
      return ServletUriComponentsBuilder.fromCurrentRequest().build().getPath().startsWith("/api/");
    } catch (Exception e) {
      LOGGER.warn("No request context available. Assuming non-api request", e);
      return false;
    }
  }
}
