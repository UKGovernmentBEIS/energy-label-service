package uk.gov.beis.els.api.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.beis.els.api.categories.airconditioners.AirConditionersInternetLabelApiForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.model.RatingClass;

@RunWith(MockitoJUnitRunner.class)
public class ApiValuesFromLegislationCategoryValidatorTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  public void validRatingClass_displayValue_assertNoErrors() {
    String validRatingClass = RatingClass.D.getDisplayValue();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertThat(errors).isEmpty();
  }

  @Test
  public void invalidRatingClass_displayValue_assertError() {
    String invalidRatingClass = RatingClass.G.getDisplayValue();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(invalidRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertThat(errors)
        .extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory.message}");
  }

  @Test
  public void validRatingClass_enumName_assertNoErrors() {
    String validRatingClass = RatingClass.D.name();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertThat(errors).isEmpty();
  }

  @Test
  public void invalidRatingClass_enumName_assertError() {
    String invalidRatingClass = RatingClass.G.name();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(invalidRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertThat(errors)
        .extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory.message}");
  }

  @Test
  public void invalidRatingClass_invalidString_assertError() {
    String invalidRatingClass = "FR-001";
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(invalidRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertThat(errors)
        .extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory.message}");
  }

  private AirConditionersInternetLabelApiForm getForm() {
    AirConditionersInternetLabelApiForm form = new AirConditionersInternetLabelApiForm();
    form.setEfficiencyRating(RatingClass.D.name());
    form.setProductPriceHeightPx(10);
    form.setLabelFormat(InternetLabelFormat.JPEG);
    form.setLabelOrientation(InternetLabelOrientation.RIGHT);
    return form;
  }
}