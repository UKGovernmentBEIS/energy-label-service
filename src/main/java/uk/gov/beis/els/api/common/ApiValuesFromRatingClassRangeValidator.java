package uk.gov.beis.els.api.common;

import java.lang.reflect.Field;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;

public class ApiValuesFromRatingClassRangeValidator implements ConstraintValidator<ApiValuesFromRatingClassRange, String> {

  private RatingClassRange range;

  @Override
  public void initialize(ApiValuesFromRatingClassRange constraintAnnotation) {
    try {
      Field field = constraintAnnotation.serviceClass().getField(constraintAnnotation.ratingClassRangeFieldName());
      range = (RatingClassRange) field.get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Cannot get RatingClassRange", e);
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; //avoid getting duplicate validation errors as this will get picked up by the existing @NotBlank annotation
    }

    try {
      return range.getApplicableRatings().contains(RatingClass.getEnum(value));
    } catch (RuntimeException e) {
      return false;
    }
  }
}
