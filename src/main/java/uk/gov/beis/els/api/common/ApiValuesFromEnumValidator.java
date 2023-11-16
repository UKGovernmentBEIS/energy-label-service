package uk.gov.beis.els.api.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ApiValuesFromEnumValidator implements ConstraintValidator<ApiValuesFromEnum, String> {

  Method method;

  @Override
  public void initialize(ApiValuesFromEnum constraintAnnotation) {
    try {
      method = constraintAnnotation.value().getMethod("getEnum", String.class);
    } catch (NoSuchMethodException e) {
      try {
        // Fall back to using Enum::ValueOf
        method = constraintAnnotation.value().getMethod("valueOf", String.class);
      } catch (NoSuchMethodException ex) {
        throw new RuntimeException(String.format("Cannot access getEnum or valueOf methods for %s", constraintAnnotation.value()), e);
      }
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; //avoid getting duplicate validation errors as this will get picked up by the existing @NotBlank annotation
    }

    Object resolvedEnum;
    try {
      resolvedEnum = method.invoke(null, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(String.format("Cannot invoke method %s from validator", method.toString()), e);
    } catch (InvocationTargetException e) { // Errors thrown by invoked method should fail validation
      return false;
    }

    return resolvedEnum != null;
  }
}
