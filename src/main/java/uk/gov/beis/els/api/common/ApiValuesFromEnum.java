package uk.gov.beis.els.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Set which values can be used in an API call from an enum
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
