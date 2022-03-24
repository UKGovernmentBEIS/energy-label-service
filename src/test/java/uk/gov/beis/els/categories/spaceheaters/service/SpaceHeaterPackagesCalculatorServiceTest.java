package uk.gov.beis.els.categories.spaceheaters.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.TankLabelClass;
import uk.gov.beis.els.categories.spaceheaters.model.TemperatureControlClass;
import uk.gov.beis.els.model.RatingClass;

public class SpaceHeaterPackagesCalculatorServiceTest {

  private SpaceHeaterPackagesCalculatorService spaceHeaterPackagesCalculatorService;

  @Before
  public void setup() {
    spaceHeaterPackagesCalculatorService = new SpaceHeaterPackagesCalculatorService();
  }


  @Test
  public void boilerPackage_givenValues_assertValues() {
    BoilerPackagesCalculatorForm form = new BoilerPackagesCalculatorForm();
    form.setSupplierName("FR Industries");
    form.setModelName("FR-042");
    form.setPreferentialHeaterHeatOutput("1235");
    form.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("85");
    form.setTemperatureControl(true);
    form.setTemperatureControlClass(TemperatureControlClass.I.name());
    form.setSupplementaryBoiler(true);
    form.setSupplementaryBoilerHeatOutput("1500");
    form.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setSolarCollectorSize("8");
    form.setSolarCollectorEfficiencyPercentage("89");
    form.setStorageTank(true);
    form.setStorageTankVolume("42");
    form.setStorageTankRating(TankLabelClass.A.name());
    form.setSpaceHeater(true);
    form.setSupplementaryHeatPump(true);
    form.setSupplementaryHeatPumpHeatOutput("2000");
    form.setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage("42");

    assertThat(spaceHeaterPackagesCalculatorService.gePreferentialHeaterEfficiencyClass(form)).isEqualTo(RatingClass.B);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.85F);
    assertThat(spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form)).isEqualTo(0.01F);
    assertThat(spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form)).isEqualTo(-0.043000005F);
    assertThat(spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(0.001264569F);
    assertThat(spaceHeaterPackagesCalculatorService.getSupplementaryHeatPumpContributionDecimal(form)).isEqualTo(-0.43000004F);
    assertThat(spaceHeaterPackagesCalculatorService.getSolarContributionAndHeatPumpDecimal(form)).isEqualTo(-0.21500002F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.6032646F);
    assertThat(spaceHeaterPackagesCalculatorService.getLowTemperatureHeatEmitters(form)).isEqualTo(1.1032646F);
  }

  @Test
  public void heatPumpPackage_givenValues_assertValues() {
    HeatPumpPackagesCalculatorForm form = new HeatPumpPackagesCalculatorForm();
    form.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage("70");
    form.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage("90");
    form.setLowTemperatureHeatPump(true);
    form.setSupplierName("FR Industries");
    form.setModelName("FR-042");
    form.setPreferentialHeaterHeatOutput("42");
    form.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("80");
    form.setTemperatureControl(true);
    form.setTemperatureControlClass(TemperatureControlClass.V.name());
    form.setSupplementaryBoiler(true);
    form.setSupplementaryBoilerHeatOutput("42");
    form.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setSolarCollectorSize("42");
    form.setSolarCollectorEfficiencyPercentage("42");
    form.setStorageTank(true);
    form.setStorageTankVolume("42");
    form.setStorageTankRating(TankLabelClass.B.name());
    form.setSpaceHeater(true);

    assertThat(spaceHeaterPackagesCalculatorService.gePreferentialHeaterEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.8F);
    assertThat(spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form)).isEqualTo(0.03F);
    assertThat(spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form)).isEqualTo(-0.0076F);
    assertThat(spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(0.0434595F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.86585945F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyColderDecimal(form)).isEqualTo(0.7658594F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyWarmerDecimal(form)).isEqualTo(0.9658594F);
  }

  @Test
  public void heatPumpPackage_givenValuesNoOptionals_assertValues() {
    HeatPumpPackagesCalculatorForm form = new HeatPumpPackagesCalculatorForm();
    form.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage("75");
    form.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage("95");
    form.setLowTemperatureHeatPump(false);
    form.setSupplierName("FR Industries");
    form.setModelName("FR-042");
    form.setPreferentialHeaterHeatOutput("42");
    form.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setTemperatureControl(false);
    form.setSupplementaryBoiler(false);
    form.setStorageTank(false);
    form.setSpaceHeater(false);

    assertThat(spaceHeaterPackagesCalculatorService.gePreferentialHeaterEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.42F);
    assertThat(spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form)).isEqualTo(0);
    assertThat(spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form)).isEqualTo(0);
    assertThat(spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(0);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.42F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyColderDecimal(form)).isEqualTo(0.75F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyWarmerDecimal(form)).isEqualTo(0.9499999F);
  }
}