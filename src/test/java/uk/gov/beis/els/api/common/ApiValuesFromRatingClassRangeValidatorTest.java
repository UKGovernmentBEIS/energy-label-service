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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.model.RatingClass;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiValuesFromRatingClassRangeValidatorTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  public void validSpinDryingEfficiencyRating_displayValue_assertNoErrors() {
    String validRatingClass = RatingClass.B.getDisplayValue();
    WashingMachinesForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertEquals(0, errors.size());
  }

  @Test
  public void invalidSpinDryingEfficiencyRating_displayValue_assertError() {
    String validRatingClass = RatingClass.APPP.getDisplayValue();
    WashingMachinesForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }

  @Test
  public void validSpinDryingEfficiencyRating_enumName_assertNoErrors() {
    String validRatingClass = RatingClass.B.name();
    WashingMachinesForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertEquals(0, errors.size());
  }

  @Test
  public void invalidSpinDryingEfficiencyRating_enumName_assertError() {
    String validRatingClass = RatingClass.APPP.name();
    WashingMachinesForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }
  @Test
  public void invalidSpinDryingEfficiencyRating_invalidString_assertError() {
    String validRatingClass = "FR";
    WashingMachinesForm form = getForm();
    form.setEfficiencyRating(validRatingClass);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }


  private WashingMachinesForm getForm() {
    WashingMachinesForm form = new WashingMachinesForm();
    form.setSupplierName("Energy Co");
    form.setModelName("FR-2000");
    form.setEfficiencyRating(RatingClass.D.getDisplayValue());
    form.setSpinDryingEfficiencyRating(RatingClass.A.name());
    form.setEnergyConsumptionPer100Cycles("10");
    form.setEcoRatedCapacity("10");
    form.setWaterConsumptionPerCycle("1");
    form.setProgrammeDurationHours("1");
    form.setProgrammeDurationMinutes("10");
    form.setNoiseEmissionClass(RatingClass.B.name());
    form.setNoiseEmissionValue("10");
    form.setQrCodeUrl("http://google.com");
    return form;
  }
}