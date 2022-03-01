package uk.gov.beis.els.api.common;

import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import uk.gov.beis.els.categories.common.LoadProfile;

public class ApiValuesFromLoadProfileListValidator implements ConstraintValidator<ApiValuesFromLoadProfileList, String> {

  List<LoadProfile> validLoadProfiles;

  @Override
  public void initialize(ApiValuesFromLoadProfileList constraintAnnotation) {
    validLoadProfiles = Arrays.asList(constraintAnnotation.values());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true; //avoid getting duplicate validation errors as this will get picked up by the existing @NotBlank annotation
    }

    try {
      return validLoadProfiles.contains(LoadProfile.getEnum(value));
    } catch (RuntimeException e) {
      return false;
    }
  }
}
