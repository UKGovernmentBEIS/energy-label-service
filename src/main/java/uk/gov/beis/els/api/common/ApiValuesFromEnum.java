package uk.gov.beis.els.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set which values can be used in an API call from an enum
 * The Enum must implement the Displayable interface
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiValuesFromEnum {
  Class<?> value();
}
