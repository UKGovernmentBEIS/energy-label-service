package uk.gov.beis.els.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import uk.gov.beis.els.categories.common.LoadProfile;

/**
 * Set which LoadProfile values can be used in an API call from a subset list of LoadProfile
 */
@Constraint(validatedBy = ApiValuesFromLoadProfileListValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiValuesFromLoadProfileList {

  String message() default "{uk.gov.beis.els.api.common.ApiValuesFromLoadProfileList.message}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  LoadProfile[] values();
}
