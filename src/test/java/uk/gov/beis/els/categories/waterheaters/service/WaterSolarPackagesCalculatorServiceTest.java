package uk.gov.beis.els.categories.waterheaters.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.beis.els.categories.common.LoadProfile;
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

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.M, 163);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingUpperLimit_assertApp() {
    RatingClass expectedRatingClass = RatingClass.APP;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.M, 162);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertApp() {
    RatingClass expectedRatingClass = RatingClass.APP;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.M, 130);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingUpperLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.M, 29);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_MLoadWaterHeatingLowerLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.M, 27);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingLowerLimit_assertAPPP() {
    RatingClass expectedRatingClass = RatingClass.APPP;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.L, 188);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingUpperLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.L, 74);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_LLoadWaterHeatingLowerLimit_assertF() {
    RatingClass expectedRatingClass = RatingClass.F;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.L, 27);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingUpperLimit_assertG() {
    RatingClass expectedRatingClass = RatingClass.G;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XL, 26);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingLowerLimit_assertC() {
    RatingClass expectedRatingClass = RatingClass.C;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XL, 38);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingUpperLimit_assertC() {
    RatingClass expectedRatingClass = RatingClass.C;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XL, 54);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XLLoadWaterHeatingLowerLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XL, 55);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingLowerLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XXL, 60);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingUpperLimit_assertB() {
    RatingClass expectedRatingClass = RatingClass.B;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XXL, 84);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingLowerLimit_assertAPP() {
    RatingClass expectedRatingClass = RatingClass.APP;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XXL, 170);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }
  @Test
  public void getWaterHeatingEfficiencyClass_XXLLoadWaterHeatingUpperLimit_assertD() {
    RatingClass expectedRatingClass = RatingClass.D;

    RatingClass returnedRatingClass = waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(LoadProfile.XXL, 36);

    assertThat(returnedRatingClass).isEqualTo(expectedRatingClass);
  }

}