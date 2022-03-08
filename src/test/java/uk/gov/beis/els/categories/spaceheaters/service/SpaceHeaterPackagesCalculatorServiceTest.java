package uk.gov.beis.els.categories.spaceheaters.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesCalculatorForm;
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
    form.setHasTemperatureControl(true);
    form.setTemperatureControlClass(TemperatureControlClass.I);
    form.setHasSupplementaryBoiler(true);
    form.setSupplementaryBoilerHeatOutput("1500");
    form.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setHasSolarCollector(true);
    form.setSolarCollectorSize("8");
    form.setSolarCollectorEfficiencyPercentage("89");
    form.setHasStorageTank(true);
    form.setStorageTankVolume("42");
    form.setStorageTankRating(TankLabelClass.A);
    form.setSpaceHeater(true);
    form.setHasSupplementaryHeatPump(true);
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
    form.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage("75");
    form.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage("95");
    form.setLowTemperatureHeatPump(true);
    form.setSupplierName("FR Industries");
    form.setModelName("FR-042");
    form.setPreferentialHeaterHeatOutput("42");
    form.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setHasTemperatureControl(true);
    form.setTemperatureControlClass(TemperatureControlClass.V);
    form.setHasSupplementaryBoiler(true);
    form.setSupplementaryBoilerHeatOutput("42");
    form.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setHasSolarCollector(true);
    form.setSolarCollectorSize("42");
    form.setSolarCollectorEfficiencyPercentage("42");
    form.setHasStorageTank(true);
    form.setStorageTankVolume("42");
    form.setStorageTankRating(TankLabelClass.B);
    form.setSpaceHeater(true);

    assertThat(spaceHeaterPackagesCalculatorService.gePreferentialHeaterEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)).isEqualTo(RatingClass.D);
    assertThat(spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.42F);
    assertThat(spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form)).isEqualTo(0.03F);
    assertThat(spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form)).isEqualTo(0.0F);
    assertThat(spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form)).isEqualTo(0.0434595F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(form)).isEqualTo(0.4934595F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyColderDecimal(form)).isEqualTo(0.089999974F);
    assertThat(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyWarmerDecimal(form)).isEqualTo(0.9499999F);
  }

  private BoilerPackagesCalculatorForm getBoilerForm() {
    BoilerPackagesCalculatorForm form = new BoilerPackagesCalculatorForm();
    form.setHasSupplementaryHeatPump(true);
    form.setSupplementaryHeatPumpHeatOutput("42");
    form.setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage("42");

    return form;
  }

  private void populateBaseForm(SpaceHeaterPackagesCalculatorForm form) {
    form.setSupplierName("FR Industries");
    form.setModelName("FR-042");
    form.setPreferentialHeaterHeatOutput("42");
    form.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setHasTemperatureControl(true);
    form.setTemperatureControlClass(TemperatureControlClass.I);
    form.setHasSupplementaryBoiler(true);
    form.setSupplementaryBoilerHeatOutput("42");
    form.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("42");
    form.setHasSolarCollector(true);
    form.setSolarCollectorSize("42");
    form.setSolarCollectorEfficiencyPercentage("42");
    form.setHasStorageTank(true);
    form.setStorageTankVolume("42");
    form.setStorageTankRating(TankLabelClass.A);
    form.setSpaceHeater(true);
  }
}