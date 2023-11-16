package uk.gov.beis.els.api.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.model.RatingClass;

@RunWith(MockitoJUnitRunner.class)
public class ApiValuesFromRatingClassRangeValidatorTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  public void validSpinDryingEfficiencyRating_displayValue_assertNoErrors() {
    String validEfficiencyRating = RatingClass.B.getDisplayValue();
    WashingMachinesForm form = getForm();
    form.setSpinDryingEfficiencyRating(validEfficiencyRating);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertThat(errors).isEmpty();
  }

  @Test
  public void invalidSpinDryingEfficiencyRating_displayValue_assertError() {
    String invalidEfficiencyRating = RatingClass.APPP.getDisplayValue();
    WashingMachinesForm form = getForm();
    form.setSpinDryingEfficiencyRating(invalidEfficiencyRating);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertThat(errors)
        .extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromRatingClassRange.message}");
  }

  @Test
  public void validSpinDryingEfficiencyRating_enumName_assertNoErrors() {
    String validEfficiencyRating = RatingClass.B.name();
    WashingMachinesForm form = getForm();
    form.setSpinDryingEfficiencyRating(validEfficiencyRating);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertThat(errors).isEmpty();
  }

  @Test
  public void invalidSpinDryingEfficiencyRating_enumName_assertError() {
    String invalidEfficiencyRating = RatingClass.APPP.name();
    WashingMachinesForm form = getForm();
    form.setSpinDryingEfficiencyRating(invalidEfficiencyRating);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertThat(errors)
        .extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromRatingClassRange.message}");
  }
  @Test
  public void invalidSpinDryingEfficiencyRating_invalidString_assertError() {
    String invalidEfficiencyRating = "FR";
    WashingMachinesForm form = getForm();
    form.setSpinDryingEfficiencyRating(invalidEfficiencyRating);

    Set<ConstraintViolation<WashingMachinesForm>> errors = validator.validate(form);

    assertThat(errors)
        .extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromRatingClassRange.message}");
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
    form.setOutputFormat("PDF");
    return form;
  }
}