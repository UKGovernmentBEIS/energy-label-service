package uk.gov.beis.els.api.common;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.beis.els.api.categories.airconditioners.AirConditionersInternetLabelApiForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelFormat;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelOrientation;
import uk.gov.beis.els.model.RatingClass;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
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

    assertEquals(0, errors.size());
  }

  @Test
  public void invalidRatingClass_displayValue_assertError() {
    String invalidRatingClass = RatingClass.G.getDisplayValue();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(invalidRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }

  @Test
  public void validRatingClass_enumName_assertNoErrors() {
    String validRatingClass = RatingClass.D.name();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertEquals(0, errors.size());
  }

  @Test
  public void invalidRatingClass_enumName_assertError() {
    String invalidRatingClass = RatingClass.G.name();
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(invalidRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }

  @Test
  public void invalidRatingClass_invalidString_assertError() {
    String invalidRatingClass = "FR-001";
    AirConditionersInternetLabelApiForm form = getForm();
    form.setEfficiencyRating(invalidRatingClass);

    Set<ConstraintViolation<AirConditionersInternetLabelApiForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }

  private AirConditionersInternetLabelApiForm getForm() {
    AirConditionersInternetLabelApiForm form = new AirConditionersInternetLabelApiForm();
    form.setEfficiencyRating(RatingClass.D.getDisplayValue());
    form.setProductPriceHeightPx(10);
    form.setLabelFormat(InternetLabelFormat.JPEG);
    form.setLabelOrientation(InternetLabelOrientation.RIGHT);
    return form;
  }
}