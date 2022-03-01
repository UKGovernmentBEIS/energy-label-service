package uk.gov.beis.els.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Fields with this annotation will have their allowableValues constraint in the OpenAPI specification set dynamically based on
 * the supplied legislation category accessor.
 *
 * This will override any static allowableValues set in a @ApiModelProperty annotation.
 */
@Constraint(validatedBy = ApiValuesFromLegislationCategoryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiValuesFromLegislationCategory{

  String message() default "{uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory.message}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  /**
   * @return The class which contains the LegislationCategory field
   */
  Class<?> serviceClass();

  /**
   * @return The name of the LegislationCategory field
   */
  String legislationCategoryFieldName() default "LEGISLATION_CATEGORY_CURRENT";

  /**
   * @return whether to use the second range of a LegislationCategory for efficiency ratings
   */
  boolean useSecondaryRange() default false;
}