package uk.gov.beis.els.api.common;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;

public class ApiValuesFromLegislationCategoryValidator implements ConstraintValidator<ApiValuesFromLegislationCategory, String> {

  private RatingClassRange range;

  @Override
  public void initialize(ApiValuesFromLegislationCategory constraintAnnotation) {
    LegislationCategory legislationCategory;
    try {
      Field field = constraintAnnotation.serviceClass().getField(constraintAnnotation.legislationCategoryFieldName());
      legislationCategory = (LegislationCategory) field.get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Cannot get LegislationCategory", e);
    }

    if (constraintAnnotation.useSecondaryRange()) {
      range = legislationCategory.getSecondaryRatingRange();
    } else {
      range = legislationCategory.getPrimaryRatingRange();
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; //avoid getting duplicate validation errors as this will get picked up by the existing @NotBlank annotation
    }

    try {
      return range.getApplicableRatings().contains(RatingClass.getEnum(value));
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
