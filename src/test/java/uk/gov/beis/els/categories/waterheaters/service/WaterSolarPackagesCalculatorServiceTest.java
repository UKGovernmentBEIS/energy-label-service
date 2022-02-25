package uk.gov.beis.els.categories.waterheaters.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCalculatorForm;
import uk.gov.beis.els.model.RatingClass;

public class WaterSolarPackagesCalculatorServiceTest {

  private WaterSolarPackagesCalculatorService waterSolarPackagesCalculatorService;

  @Before
  public void setUp() {
    waterSolarPackagesCalculatorService = new WaterSolarPackagesCalculatorService();
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertAppp() {
    RatingClass expectedRatingClass = RatingClass.APPP;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("163");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingUpperLimit_assertApp() {
    RatingClass expectedRatingClass = RatingClass.APP;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("162");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertApp() {
    RatingClass expectedRatingClass = RatingClass.APP;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("130");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingUpperLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("29");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setWaterHeatingEfficiencyPercentage("27");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingLowerLimit_assertAPPP() {
    RatingClass expectedRatingClass = RatingClass.APPP;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setWaterHeatingEfficiencyPercentage("188");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingUpperLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setWaterHeatingEfficiencyPercentage("74");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingLowerLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setWaterHeatingEfficiencyPercentage("27");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingUpperLimit_assertG() {
    RatingClass expectedRatingClass = RatingClass.G;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("26");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingLowerLimit_assertC() {
    RatingClass expectedRatingClass = RatingClass.C;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("38");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingUpperLimit_assertC() {
    RatingClass expectedRatingClass = RatingClass.C;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("54");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingLowerLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setWaterHeatingEfficiencyPercentage("55");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingLowerLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("60");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingUpperLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("84");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingLowerLimit_assertAPP() {
    RatingClass expectedRatingClass = RatingClass.APP;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("170");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingUpperLimit_assertD() {
    RatingClass expectedRatingClass = RatingClass.D;
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setDeclaredLoadProfile(LoadProfile.XXL.name());
    form.setWaterHeatingEfficiencyPercentage("36");

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getAllValues_formWithValues_AssertRightValues_A() {
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setWaterHeatingEfficiencyPercentage("96");
    form.setDeclaredLoadProfile(LoadProfile.M.name());
    form.setStorageTank(false);
    form.setAnnualNonSolarHeatContribution("900");
    form.setAuxElectricityConsumption("541");

    assertThat(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form)).isEqualTo(RatingClass.A);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyClass(form)).isEqualTo(RatingClass.E);
    assertThat(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyDecimal(form)).isEqualTo(0.96F);
    assertThat(waterSolarPackagesCalculatorService.getNonSolarScalingFactor(form)).isEqualTo(1.4287777F);
    assertThat(waterSolarPackagesCalculatorService.getAuxElectricityConsumptionProportionDecimal(form)).isEqualTo(1.0517926F);
    assertThat(waterSolarPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(-0.6458811F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyDecimal(form)).isEqualTo(0.31411886F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyColderDecimal(form)).isEqualTo(0.4432951F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyWarmerDecimal(form)).isEqualTo(0.055766404F);
  }

  @Test
  public void getAllValues_formWithValues_AssertRightValues_B() {
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setWaterHeatingEfficiencyPercentage("24");
    form.setDeclaredLoadProfile(LoadProfile.XL.name());
    form.setStorageTank(true);
    form.setAnnualNonSolarHeatContribution("800");
    form.setAuxElectricityConsumption("241");

    assertThat(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form)).isEqualTo(RatingClass.G);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyClass(form)).isEqualTo(RatingClass.B);
    assertThat(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyDecimal(form)).isEqualTo(0.24F);
    assertThat(waterSolarPackagesCalculatorService.getNonSolarScalingFactor(form)).isEqualTo(5.24425F);
    assertThat(waterSolarPackagesCalculatorService.getAuxElectricityConsumptionProportionDecimal(form)).isEqualTo(0.14360966F);
    assertThat(waterSolarPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(0.47644734F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyDecimal(form)).isEqualTo(0.71644735F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyColderDecimal(form)).isEqualTo(0.6211579F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyWarmerDecimal(form)).isEqualTo(0.9070263F);
  }

  @Test
  public void getAllValues_formWithValues_AssertRightValues_C() {
    WaterSolarPackagesCalculatorForm form = getPackageForm();
    form.setWaterHeatingEfficiencyPercentage("35");
    form.setDeclaredLoadProfile(LoadProfile.L.name());
    form.setStorageTank(true);
    form.setAnnualNonSolarHeatContribution("600");
    form.setAuxElectricityConsumption("141");

    assertThat(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyClass(form)).isEqualTo(RatingClass.A);
    assertThat(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyDecimal(form)).isEqualTo(0.35F);
    assertThat(waterSolarPackagesCalculatorService.getNonSolarScalingFactor(form)).isEqualTo(4.2735F);
    assertThat(waterSolarPackagesCalculatorService.getAuxElectricityConsumptionProportionDecimal(form)).isEqualTo(0.13747515F);
    assertThat(waterSolarPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(0.7304723F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyDecimal(form)).isEqualTo(1.0804724F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyColderDecimal(form)).isEqualTo(0.9343779F);
    assertThat(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyWarmerDecimal(form)).isEqualTo(1.3726614F);
  }

  private WaterSolarPackagesCalculatorForm getPackageForm() {
    WaterSolarPackagesCalculatorForm form = new WaterSolarPackagesCalculatorForm();
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