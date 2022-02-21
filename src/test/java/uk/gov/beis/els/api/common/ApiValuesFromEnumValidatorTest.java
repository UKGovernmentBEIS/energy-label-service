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
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ClimateClass;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ProRefrigeratedCabinetsForm;
import uk.gov.beis.els.model.RatingClass;

@RunWith(MockitoJUnitRunner.class)
public class ApiValuesFromEnumValidatorTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  public void validEnumValue_displayName_assertNoErrors() {
    String validEnum = ClimateClass.THREE.getDisplayName();
    ProRefrigeratedCabinetsForm form = getForm();
    form.setClimateClass(validEnum);

    Set<ConstraintViolation<ProRefrigeratedCabinetsForm>> errors = validator.validate(form);

    assertEquals(0, errors.size());
  }

  @Test
  public void validEnumValue_enumName_assertNoErrors() {
    String validEnum = ClimateClass.THREE.name();
    ProRefrigeratedCabinetsForm form = getForm();
    form.setClimateClass(validEnum);

    Set<ConstraintViolation<ProRefrigeratedCabinetsForm>> errors = validator.validate(form);

    assertEquals(0, errors.size());
  }

  @Test
  public void invalidValue_assertError() {
    String validEnum = "FR-002";
    ProRefrigeratedCabinetsForm form = getForm();
    form.setClimateClass(validEnum);

    Set<ConstraintViolation<ProRefrigeratedCabinetsForm>> errors = validator.validate(form);

    assertEquals(1, errors.size());
  }

  private ProRefrigeratedCabinetsForm getForm() {
    ProRefrigeratedCabinetsForm form = new ProRefrigeratedCabinetsForm();
    form.setEfficiencyRating(RatingClass.A.name());
    form.setAnnualEnergyConsumption("1");
    form.setChilledCompartment(false);
    form.setFrozenCompartment(false);
    form.setClimateClass(ClimateClass.THREE.name());
    form.setSupplierName("Energy Co");
    form.setModelName("FR-2022");
    return form;
  }
}