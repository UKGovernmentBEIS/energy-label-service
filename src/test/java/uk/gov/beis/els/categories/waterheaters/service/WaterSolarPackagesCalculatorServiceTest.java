package uk.gov.beis.els.categories.waterheaters.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.waterheaters.model.WaterHeaterPackageCalculatorForm;
import uk.gov.beis.els.model.RatingClass;

@RunWith(MockitoJUnitRunner.class)
public class WaterSolarPackagesCalculatorServiceTest {

  private WaterSolarPackagesCalculatorService waterSolarPackagesCalculatorService;

  @Before
  public void setUp() {
    waterSolarPackagesCalculatorService = new WaterSolarPackagesCalculatorService();
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertAppp() {
    RatingClass expectedRatingClass = RatingClass.APPP;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("163");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingUpperLimit_assertApp() {
    RatingClass expectedRatingClass = RatingClass.APP;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("162");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertApp() {
    RatingClass expectedRatingClass = RatingClass.APP;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("130");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingUpperLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("29");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("27");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingLowerLimit_assertAPPP() {
    RatingClass expectedRatingClass = RatingClass.APPP;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setWaterHeatingEfficiencyPercentage("188");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingUpperLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setWaterHeatingEfficiencyPercentage("74");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingLowerLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setWaterHeatingEfficiencyPercentage("27");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingUpperLimit_assertG() {
    RatingClass expectedRatingClass = RatingClass.G;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("26");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingLowerLimit_assertC() {
    RatingClass expectedRatingClass = RatingClass.C;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("38");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingUpperLimit_assertC() {
    RatingClass expectedRatingClass = RatingClass.C;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("54");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingLowerLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("55");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingLowerLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("60");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingUpperLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("84");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingLowerLimit_assertAPP() {
    RatingClass expectedRatingClass = RatingClass.APP;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("170");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingUpperLimit_assertD() {
    RatingClass expectedRatingClass = RatingClass.D;
    WaterHeaterPackageCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("36");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  private WaterHeaterPackageCalculatorForm getPackageForm() {
    WaterHeaterPackageCalculatorForm form = new WaterHeaterPackageCalculatorForm();
    form.setSupplierName("FR Energy Corp");
    form.setModelName("FR-5000");
    form.setWaterHeatingEfficiencyPercentage("120");
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setStorageTank(true);
    form.setAnnualNonSolarHeatContribution("585");
    form.setAuxElectricityConsumption("46");
    return form;
  }
}