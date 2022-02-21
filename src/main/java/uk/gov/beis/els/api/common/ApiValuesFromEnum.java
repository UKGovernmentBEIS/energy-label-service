package uk.gov.beis.els.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Set which values can be used in an API call from an enum
 * The Enum must implement the Displayable interface
 */
@Constraint(validatedBy = ApiValuesFromEnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiValuesFromEnum {

  String message() default "{uk.gov.beis.els.api.common.ApiValuesFromEnum.message}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  Class<?> value();
}
