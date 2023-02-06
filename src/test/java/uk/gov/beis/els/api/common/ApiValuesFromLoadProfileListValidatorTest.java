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
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCalculatorForm;

@RunWith(MockitoJUnitRunner.class)
public class ApiValuesFromLoadProfileListValidatorTest {

  private Validator validator;

  @Before
  public void setUp() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  public void loadProfileInRange_enumName_assertNoErrors() {
    String validLoadProfile = LoadProfile.M.name();
    WaterSolarPackagesCalculatorForm form = getForm();
    form.setDeclaredLoadProfile(validLoadProfile);

    Set<ConstraintViolation<WaterSolarPackagesCalculatorForm>> errors = validator.validate(form);

    assertThat(errors).isEmpty();
  }

  @Test
  public void loadProfileOutOfRange_enumName_assertError() {
    String invalidLoadProfile = LoadProfile.S.name();
    WaterSolarPackagesCalculatorForm form = getForm();
    form.setDeclaredLoadProfile(invalidLoadProfile);

    Set<ConstraintViolation<WaterSolarPackagesCalculatorForm>> errors = validator.validate(form);

    assertThat(errors).
        extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromLoadProfileList.message}");
  }

  @Test
  public void loadProfileInRange_displayName_assertNoErrors() {
    String validLoadProfile = LoadProfile.L.getDisplayName();
    WaterSolarPackagesCalculatorForm form = getForm();
    form.setDeclaredLoadProfile(validLoadProfile);

    Set<ConstraintViolation<WaterSolarPackagesCalculatorForm>> errors = validator.validate(form);

    assertThat(errors).isEmpty();
  }

  @Test
  public void loadProfileOutOfRange_displayName_assertError() {
    String validLoadProfile = LoadProfile.XXS.getDisplayName();
    WaterSolarPackagesCalculatorForm form = getForm();
    form.setDeclaredLoadProfile(validLoadProfile);

    Set<ConstraintViolation<WaterSolarPackagesCalculatorForm>> errors = validator.validate(form);

    assertThat(errors).
        extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromLoadProfileList.message}");
  }

  @Test
  public void invalidString_assertError() {
    String invalidString = "anyString";
    WaterSolarPackagesCalculatorForm form = getForm();
    form.setDeclaredLoadProfile(invalidString);

    Set<ConstraintViolation<WaterSolarPackagesCalculatorForm>> errors = validator.validate(form);

    assertThat(errors).
        extracting(ConstraintViolation::getMessageTemplate)
        .containsExactly("{uk.gov.beis.els.api.common.ApiValuesFromLoadProfileList.message}");
  }

  private WaterSolarPackagesCalculatorForm getForm() {
    WaterSolarPackagesCalculatorForm form = new WaterSolarPackagesCalculatorForm();
    form.setWaterHeatingEfficiencyPercentage("100");
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setStorageTank(true);
    form.setAnnualNonSolarHeatContribution("4242");
    form.setAuxElectricityConsumption("4242");
    form.setSupplierName("FR Industries");
    form.setModelName("FR-0042");
    form.setOutputFormat("PDF");
    return form;
  }
}