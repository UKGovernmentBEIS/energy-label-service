package uk.gov.beis.els.api.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import uk.gov.beis.els.model.Displayable;

public class ApiValuesFromEnumValidator implements ConstraintValidator<ApiValuesFromEnum, String> {

  Method method;

  @Override
  public void initialize(ApiValuesFromEnum constraintAnnotation) {
    try {
      method = constraintAnnotation.value().getMethod("getEnum", String.class);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(String.format("Cannot access getEnum method in %s", constraintAnnotation.value()), e);
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; //avoid getting duplicate validation errors as this will get picked up by the existing @NotBlank annotation
    }

    Displayable resolvedEnum;
    try {
      resolvedEnum = (Displayable) method.invoke(null, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Cannot invoke getEnum()", e);
    } catch (InvocationTargetException e) { //We ignore the IllegalArgumentException from getEnum() as that gets wrapped in an InvocationTargetException
      return false;
    }

    return resolvedEnum != null;
  }
}
