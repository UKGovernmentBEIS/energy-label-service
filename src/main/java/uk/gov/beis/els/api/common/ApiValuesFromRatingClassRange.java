package uk.gov.beis.els.api.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields with this annotation will have their allowableValues constraint in the OpenAPI specification set dynamically based on
 * the supplied RatingClassRange category accessor.
 *
 * This will override any static allowableValues set in a @ApiModelProperty annotation.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiValuesFromRatingClassRange {

  /**
   * @return The class which contains the RatingClassRange field
   */
  Class<?> serviceClass();

  /**
   * @return The name of the RatingClassRange field
   */
  String ratingClassRangeFieldName();
}