package uk.gov.beis.els.categories.spaceheaters.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.CombinationHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationCalculatorForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.service.TemplateParserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpaceHeatersServiceTest {

  @Mock
  private TemplateParserService templateParserService;
  @Mock
  private SpaceHeaterPackagesCalculatorService spaceHeaterPackagesCalculatorService;
  @Mock
  private WaterHeatersService waterHeatersService;

  private SpaceHeatersService spaceHeatersService;

  @Before
  public void setUp() {
    when(spaceHeaterPackagesCalculatorService.gePreferentialHeaterEfficiencyClass(any()))
        .thenReturn(RatingClass.A);
    when(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(any()))
        .thenReturn(RatingClass.B);

    WaterSolarPackagesForm waterSolarForm = new WaterSolarPackagesForm();
    waterSolarForm.setHeaterEfficiencyRating("C");
    waterSolarForm.setPackageEfficiencyRating("D");
    when(waterHeatersService.toWaterSolarPackagesForm(any(BoilerCombinationCalculatorForm.class)))
        .thenReturn(waterSolarForm);
    when(waterHeatersService.toWaterSolarPackagesForm(any(HeatPumpCombinationCalculatorForm.class)))
        .thenReturn(waterSolarForm);

    spaceHeatersService = new SpaceHeatersService(templateParserService, spaceHeaterPackagesCalculatorService, waterHeatersService);

  }

  @Test
  public void toCombinationHeaterPackagesForm_Boiler_AllFields() {
    BoilerCombinationCalculatorForm sourceForm = new BoilerCombinationCalculatorForm();
    sourceForm.setSupplierName("supplier-name");
    sourceForm.setModelName("model-name");
    sourceForm.setPreferentialHeaterHeatOutput("100");
    sourceForm.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("80");
    sourceForm.setSolarCollectorSize("25");
    sourceForm.setSolarCollectorEfficiencyPercentage("85");
    sourceForm.setAnnualNonSolarHeatContribution("30");
    sourceForm.setAuxElectricityConsumption("20");
    sourceForm.setWaterHeatingEfficiencyPercentage("80");
    sourceForm.setDeclaredLoadProfile("XL");

    sourceForm.setTemperatureControl(true);
    sourceForm.setTemperatureControlClass("IV");

    sourceForm.setSupplementaryBoiler(true);
    sourceForm.setSupplementaryBoilerHeatOutput("500");
    sourceForm.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("70");

    sourceForm.setStorageTank(true);
    sourceForm.setStorageTankVolume("50");
    sourceForm.setStorageTankRating("B");

    sourceForm.setSpaceHeater(true);

    sourceForm.setSupplementaryHeatPump(true);
    sourceForm.setSupplementaryHeatPumpHeatOutput("400");
    sourceForm.setSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage("65");

    sourceForm.setOutputFormat("PNG");
    sourceForm.setGoogleAnalyticsClientId("123");

    CombinationHeaterPackagesForm targetForm = spaceHeatersService.toCombinationHeaterPackagesForm(sourceForm);

    assertThat(targetForm.getSupplierName()).isEqualTo("supplier-name");
    assertThat(targetForm.getModelName()).isEqualTo("model-name");
    assertThat(targetForm.getSpaceHeaterEfficiencyRating()).isEqualTo("A");
    assertThat(targetForm.getSolarCollector()).isTrue();
    assertThat(targetForm.getHotWaterStorageTank()).isTrue();
    assertThat(targetForm.getTemperatureControl()).isTrue();
    assertThat(targetForm.getSpaceHeater()).isTrue();
    assertThat(targetForm.getPackageSpaceHeatingEfficiencyRating()).isEqualTo("B");
    assertThat(targetForm.getWaterHeaterEfficiencyRating()).isEqualTo("C");
    assertThat(targetForm.getHeaterDeclaredLoadProfile()).isEqualTo("XL");
    assertThat(targetForm.getPackageWaterHeatingEfficiencyRating()).isEqualTo("D");
    assertThat(targetForm.getPackageDeclaredLoadProfile()).isEqualTo("XL");
    assertThat(targetForm.getOutputFormat()).isEqualTo("PNG");
    assertThat(targetForm.getGoogleAnalyticsClientId()).isEqualTo("123");
  }

  @Test
  public void toCombinationHeaterPackagesForm_Boiler_MinimalFields() {
    BoilerCombinationCalculatorForm sourceForm = new BoilerCombinationCalculatorForm();
    sourceForm.setSupplierName("supplier-name");
    sourceForm.setModelName("model-name");
    sourceForm.setPreferentialHeaterHeatOutput("100");
    sourceForm.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("80");
    sourceForm.setSolarCollectorSize("25");
    sourceForm.setSolarCollectorEfficiencyPercentage("85");
    sourceForm.setAnnualNonSolarHeatContribution("30");
    sourceForm.setAuxElectricityConsumption("20");
    sourceForm.setWaterHeatingEfficiencyPercentage("80");
    sourceForm.setDeclaredLoadProfile("M");

    sourceForm.setTemperatureControl(false);
    sourceForm.setSupplementaryBoiler(false);
    sourceForm.setStorageTank(false);
    sourceForm.setSpaceHeater(false);
    sourceForm.setSupplementaryHeatPump(false);

    sourceForm.setOutputFormat("PNG");
    sourceForm.setGoogleAnalyticsClientId("123");

    CombinationHeaterPackagesForm targetForm = spaceHeatersService.toCombinationHeaterPackagesForm(sourceForm);

    assertThat(targetForm.getSupplierName()).isEqualTo("supplier-name");
    assertThat(targetForm.getModelName()).isEqualTo("model-name");
    assertThat(targetForm.getSpaceHeaterEfficiencyRating()).isEqualTo("A");
    assertThat(targetForm.getSolarCollector()).isTrue();
    assertThat(targetForm.getHotWaterStorageTank()).isFalse();
    assertThat(targetForm.getTemperatureControl()).isFalse();
    assertThat(targetForm.getSpaceHeater()).isFalse();
    assertThat(targetForm.getPackageSpaceHeatingEfficiencyRating()).isEqualTo("B");
    assertThat(targetForm.getWaterHeaterEfficiencyRating()).isEqualTo("C");
    assertThat(targetForm.getHeaterDeclaredLoadProfile()).isEqualTo("M");
    assertThat(targetForm.getPackageWaterHeatingEfficiencyRating()).isEqualTo("D");
    assertThat(targetForm.getPackageDeclaredLoadProfile()).isEqualTo("M");
    assertThat(targetForm.getOutputFormat()).isEqualTo("PNG");
    assertThat(targetForm.getGoogleAnalyticsClientId()).isEqualTo("123");
  }

  @Test
  public void toCombinationHeaterPackagesForm_HeatPump_AllFields() {
    HeatPumpCombinationCalculatorForm sourceForm = new HeatPumpCombinationCalculatorForm();
    sourceForm.setSupplierName("supplier-name");
    sourceForm.setModelName("model-name");
    sourceForm.setPreferentialHeaterHeatOutput("100");
    sourceForm.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage("70");
    sourceForm.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("80");
    sourceForm.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage("85");
    sourceForm.setLowTemperatureHeatPump(true);

    sourceForm.setSolarCollectorSize("25");
    sourceForm.setSolarCollectorEfficiencyPercentage("85");
    sourceForm.setAnnualNonSolarHeatContribution("30");
    sourceForm.setAuxElectricityConsumption("20");
    sourceForm.setWaterHeatingEfficiencyPercentage("80");
    sourceForm.setDeclaredLoadProfile("XL");

    sourceForm.setTemperatureControl(true);
    sourceForm.setTemperatureControlClass("IV");

    sourceForm.setSupplementaryBoiler(true);
    sourceForm.setSupplementaryBoilerHeatOutput("500");
    sourceForm.setSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage("70");

    sourceForm.setStorageTank(true);
    sourceForm.setStorageTankVolume("50");
    sourceForm.setStorageTankRating("B");

    sourceForm.setSpaceHeater(true);

    sourceForm.setOutputFormat("PNG");
    sourceForm.setGoogleAnalyticsClientId("123");

    CombinationHeaterPackagesForm targetForm = spaceHeatersService.toCombinationHeaterPackagesForm(sourceForm);

    assertThat(targetForm.getSupplierName()).isEqualTo("supplier-name");
    assertThat(targetForm.getModelName()).isEqualTo("model-name");
    assertThat(targetForm.getSpaceHeaterEfficiencyRating()).isEqualTo("A");
    assertThat(targetForm.getSolarCollector()).isTrue();
    assertThat(targetForm.getHotWaterStorageTank()).isTrue();
    assertThat(targetForm.getTemperatureControl()).isTrue();
    assertThat(targetForm.getSpaceHeater()).isTrue();
    assertThat(targetForm.getPackageSpaceHeatingEfficiencyRating()).isEqualTo("B");
    assertThat(targetForm.getWaterHeaterEfficiencyRating()).isEqualTo("C");
    assertThat(targetForm.getHeaterDeclaredLoadProfile()).isEqualTo("XL");
    assertThat(targetForm.getPackageWaterHeatingEfficiencyRating()).isEqualTo("D");
    assertThat(targetForm.getPackageDeclaredLoadProfile()).isEqualTo("XL");
    assertThat(targetForm.getOutputFormat()).isEqualTo("PNG");
    assertThat(targetForm.getGoogleAnalyticsClientId()).isEqualTo("123");
  }

  @Test
  public void toCombinationHeaterPackagesForm_HeatPump_MinimalFields() {
    HeatPumpCombinationCalculatorForm sourceForm = new HeatPumpCombinationCalculatorForm();
    sourceForm.setSupplierName("supplier-name");
    sourceForm.setModelName("model-name");
    sourceForm.setPreferentialHeaterHeatOutput("100");
    sourceForm.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage("70");
    sourceForm.setPreferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage("80");
    sourceForm.setPreferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage("85");
    sourceForm.setLowTemperatureHeatPump(true);

    sourceForm.setSolarCollectorSize("25");
    sourceForm.setSolarCollectorEfficiencyPercentage("85");
    sourceForm.setAnnualNonSolarHeatContribution("30");
    sourceForm.setAuxElectricityConsumption("20");
    sourceForm.setWaterHeatingEfficiencyPercentage("80");
    sourceForm.setDeclaredLoadProfile("XL");

    sourceForm.setTemperatureControl(false);
    sourceForm.setSupplementaryBoiler(false);
    sourceForm.setStorageTank(false);
    sourceForm.setSpaceHeater(false);

    sourceForm.setOutputFormat("PNG");
    sourceForm.setGoogleAnalyticsClientId("123");

    CombinationHeaterPackagesForm targetForm = spaceHeatersService.toCombinationHeaterPackagesForm(sourceForm);

    assertThat(targetForm.getSupplierName()).isEqualTo("supplier-name");
    assertThat(targetForm.getModelName()).isEqualTo("model-name");
    assertThat(targetForm.getSpaceHeaterEfficiencyRating()).isEqualTo("A");
    assertThat(targetForm.getSolarCollector()).isTrue();
    assertThat(targetForm.getHotWaterStorageTank()).isFalse();
    assertThat(targetForm.getTemperatureControl()).isFalse();
    assertThat(targetForm.getSpaceHeater()).isFalse();
    assertThat(targetForm.getPackageSpaceHeatingEfficiencyRating()).isEqualTo("B");
    assertThat(targetForm.getWaterHeaterEfficiencyRating()).isEqualTo("C");
    assertThat(targetForm.getHeaterDeclaredLoadProfile()).isEqualTo("XL");
    assertThat(targetForm.getPackageWaterHeatingEfficiencyRating()).isEqualTo("D");
    assertThat(targetForm.getPackageDeclaredLoadProfile()).isEqualTo("XL");
    assertThat(targetForm.getOutputFormat()).isEqualTo("PNG");
    assertThat(targetForm.getGoogleAnalyticsClientId()).isEqualTo("123");
  }

}
