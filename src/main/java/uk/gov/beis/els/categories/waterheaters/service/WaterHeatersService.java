package uk.gov.beis.els.categories.waterheaters.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.api.categories.waterheaters.ConventionalWaterHeaterApiForm;
import uk.gov.beis.els.api.categories.waterheaters.HeatPumpWaterHeatersApiForm;
import uk.gov.beis.els.api.categories.waterheaters.SolarWaterHeatersApiForm;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.waterheaters.model.ClimateConditionForm;
import uk.gov.beis.els.categories.waterheaters.model.ConventionalWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.HotWaterStorageTanksForm;
import uk.gov.beis.els.categories.waterheaters.model.SolarWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesCalculatorForm;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class WaterHeatersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.AP, RatingClass.F));

  public static final LegislationCategory LEGISLATION_CATEGORY_SOLAR_PACKAGES = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G),
    RatingClassRange.of(RatingClass.AP, RatingClass.G)
  );

  private static final Map<LoadProfile, String> LOAD_PROFILE_SVG_IDS;
  private static final Map<RatingClass, String> RATING_CLASS_SVG_IDS;

  static {
    Map<LoadProfile, String> loadProfileSvgIdMap = new HashMap<>();
    loadProfileSvgIdMap.put(LoadProfile.M, "loadProfileTickM");
    loadProfileSvgIdMap.put(LoadProfile.L, "loadProfileTickL");
    loadProfileSvgIdMap.put(LoadProfile.XL, "loadProfileTickXL");
    loadProfileSvgIdMap.put(LoadProfile.XXL, "loadProfileTickXXL");
    LOAD_PROFILE_SVG_IDS = Collections.unmodifiableMap(loadProfileSvgIdMap);

    Map<RatingClass, String> ratingClassSvgIdMap = new HashMap<>();
    ratingClassSvgIdMap.put(RatingClass.APPP, "labelClassTickA+++");
    ratingClassSvgIdMap.put(RatingClass.APP, "labelClassTickA++");
    ratingClassSvgIdMap.put(RatingClass.AP, "labelClassTickA+");
    ratingClassSvgIdMap.put(RatingClass.A, "labelClassTickA");
    ratingClassSvgIdMap.put(RatingClass.B, "labelClassTickB");
    ratingClassSvgIdMap.put(RatingClass.C, "labelClassTickC");
    ratingClassSvgIdMap.put(RatingClass.D, "labelClassTickD");
    ratingClassSvgIdMap.put(RatingClass.E, "labelClassTickE");
    ratingClassSvgIdMap.put(RatingClass.F, "labelClassTickF");
    ratingClassSvgIdMap.put(RatingClass.G, "labelClassTickG");
    RATING_CLASS_SVG_IDS = Collections.unmodifiableMap(ratingClassSvgIdMap);
  }


  private final TemplateParserService templateParserService;
  private final WaterSolarPackagesCalculatorService waterSolarPackagesCalculatorService;

  public static final List<LoadProfile> WATER_SOLAR_PACKAGES_LOAD_PROFILES = Arrays.asList(LoadProfile.M, LoadProfile.L,
      LoadProfile.XL, LoadProfile.XXL);

  @Autowired
  public WaterHeatersService(TemplateParserService templateParserService,
                             WaterSolarPackagesCalculatorService waterSolarPackagesCalculatorService) {
    this.templateParserService = templateParserService;
    this.waterSolarPackagesCalculatorService = waterSolarPackagesCalculatorService;
  }

  public ProcessedEnergyLabelDocument generateHtml(HeatPumpWaterHeatersForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/heat-pump-water-heaters.svg"));

    if (StringUtils.isBlank(form.getSoundPowerLevelIndoors())) {
      templatePopulator.removeElementById("insideDbSection");

    } else {
      templatePopulator
          .applyCssClassToId("insideDbSection", "hasInsideDb")
          .setText("insideDb", form.getSoundPowerLevelIndoors());
    }

    if (BooleanUtils.isTrue(form.getCanRunOffPeakOnly())) {
     templatePopulator.applyCssClassToId("offPeakMode","hasOffPeakMode");
    }

    populateClimateConditions(form, templatePopulator);

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_HEAT_PUMP, form);
  }

  public HeatPumpWaterHeatersForm toHeatPumpWaterHeatersForm(HeatPumpWaterHeatersApiForm heatPumpWaterHeatersApiForm) {
    HeatPumpWaterHeatersForm form = new HeatPumpWaterHeatersForm();
    form.setSupplierName(heatPumpWaterHeatersApiForm.getSupplierName());
    form.setModelName(heatPumpWaterHeatersApiForm.getModelName());
    form.setDeclaredLoadProfile(heatPumpWaterHeatersApiForm.getDeclaredLoadProfile());
    form.setEfficiencyRating(heatPumpWaterHeatersApiForm.getEfficiencyRating());
    form.setSoundPowerLevelIndoors(heatPumpWaterHeatersApiForm.getSoundPowerLevelIndoors());
    form.setSoundPowerLevelOutdoors(heatPumpWaterHeatersApiForm.getSoundPowerLevelOutdoors());
    form.setConsumptionUnit(heatPumpWaterHeatersApiForm.getConsumptionUnit());
    if (heatPumpWaterHeatersApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.KWH.name())) {
      form.setColderKwhAnnumSingle(heatPumpWaterHeatersApiForm.getColderKwhAnnum());
      form.setAverageKwhAnnumSingle(heatPumpWaterHeatersApiForm.getAverageKwhAnnum());
      form.setWarmerKwhAnnumSingle(heatPumpWaterHeatersApiForm.getWarmerKwhAnnum());
    } else if (heatPumpWaterHeatersApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.GJ.name())) {
      form.setColderGjAnnumSingle(heatPumpWaterHeatersApiForm.getColderGjAnnum());
      form.setAverageGjAnnumSingle(heatPumpWaterHeatersApiForm.getAverageGjAnnum());
      form.setWarmerGjAnnumSingle(heatPumpWaterHeatersApiForm.getWarmerGjAnnum());
    } else if (heatPumpWaterHeatersApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.BOTH.name())) {
      form.setColderKwhAnnumBoth(heatPumpWaterHeatersApiForm.getColderKwhAnnum());
      form.setAverageKwhAnnumBoth(heatPumpWaterHeatersApiForm.getAverageKwhAnnum());
      form.setWarmerKwhAnnumBoth(heatPumpWaterHeatersApiForm.getWarmerKwhAnnum());
      form.setColderGjAnnumBoth(heatPumpWaterHeatersApiForm.getColderGjAnnum());
      form.setAverageGjAnnumBoth(heatPumpWaterHeatersApiForm.getAverageGjAnnum());
      form.setWarmerGjAnnumBoth(heatPumpWaterHeatersApiForm.getWarmerGjAnnum());
    }
    form.setCanRunOffPeakOnly(heatPumpWaterHeatersApiForm.getCanRunOffPeakOnly());
    return form;
  }

  public ProcessedEnergyLabelDocument generateHtml(ConventionalWaterHeatersForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/conventional-water-heaters.svg"));

    if (BooleanUtils.isTrue(form.getOffPeak())) {
      templatePopulator.applyCssClassToId("offPeakMode","hasOffPeakMode");
    }

    EnergyConsumptionUnit energyConsumptionUnit = EnergyConsumptionUnit.valueOf(form.getConsumptionUnit());

    if (energyConsumptionUnit == EnergyConsumptionUnit.KWH) {
      templatePopulator
          .setText("kwhAnnum", form.getKwhAnnum())
          .removeElementById("gjAnnumGroup");

    } else if (EnergyConsumptionUnit.GJ.name().equals(form.getConsumptionUnit())) {
      templatePopulator
          .setText("gjAnnum", form.getGjAnnum())
          .removeElementById("kwAnnumGroup");
    } else if (EnergyConsumptionUnit.BOTH.name().equals(form.getConsumptionUnit())) {
      templatePopulator
          .setText("kwhAnnum", form.getBothKwhAnnum())
          .setText("gjAnnum", form.getBothGjAnnum());
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_CONVENTIONAL, form);
  }

  public ConventionalWaterHeatersForm toConventionWaterHeatersForm(ConventionalWaterHeaterApiForm conventionalWaterHeaterApiForm) {
    ConventionalWaterHeatersForm form = new ConventionalWaterHeatersForm();
    form.setSupplierName(conventionalWaterHeaterApiForm.getSupplierName());
    form.setModelName(conventionalWaterHeaterApiForm.getModelName());
    form.setDeclaredLoadProfile(conventionalWaterHeaterApiForm.getDeclaredLoadProfile());
    form.setEfficiencyRating(conventionalWaterHeaterApiForm.getEfficiencyRating());
    form.setConsumptionUnit(conventionalWaterHeaterApiForm.getConsumptionUnit());
    if (conventionalWaterHeaterApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.KWH.name())) {
      form.setKwhAnnum(conventionalWaterHeaterApiForm.getKwhAnnum());
    } else if (conventionalWaterHeaterApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.GJ.name())) {
      form.setGjAnnum(conventionalWaterHeaterApiForm.getGjAnnum());
    } else if (conventionalWaterHeaterApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.BOTH.name())) {
      form.setBothKwhAnnum(conventionalWaterHeaterApiForm.getKwhAnnum());
      form.setBothGjAnnum(conventionalWaterHeaterApiForm.getGjAnnum());
    }
    form.setSoundPowerLevelIndoors(conventionalWaterHeaterApiForm.getSoundPowerLevelIndoors());
    form.setOffPeak(conventionalWaterHeaterApiForm.getOffPeak());
    return form;
  }

  public ProcessedEnergyLabelDocument generateHtml(SolarWaterHeatersForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/solar-water-heaters.svg"));

    populateClimateConditions(form, templatePopulator);

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_SOLAR, form);
  }

  public SolarWaterHeatersForm toSolarWaterHeatersForm(SolarWaterHeatersApiForm solarWaterHeatersApiForm) {
    SolarWaterHeatersForm form = new SolarWaterHeatersForm();
    form.setSupplierName(solarWaterHeatersApiForm.getSupplierName());
    form.setModelName(solarWaterHeatersApiForm.getModelName());
    form.setDeclaredLoadProfile(solarWaterHeatersApiForm.getDeclaredLoadProfile());
    form.setEfficiencyRating(solarWaterHeatersApiForm.getEfficiencyRating());
    form.setSoundPowerLevelIndoors(solarWaterHeatersApiForm.getSoundPowerLevelIndoors());
    form.setConsumptionUnit(solarWaterHeatersApiForm.getConsumptionUnit());
    if (solarWaterHeatersApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.KWH.name())) {
      form.setColderKwhAnnumSingle(solarWaterHeatersApiForm.getColderKwhAnnum());
      form.setAverageKwhAnnumSingle(solarWaterHeatersApiForm.getAverageKwhAnnum());
      form.setWarmerKwhAnnumSingle(solarWaterHeatersApiForm.getWarmerKwhAnnum());
    } else if (solarWaterHeatersApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.GJ.name())) {
      form.setColderGjAnnumSingle(solarWaterHeatersApiForm.getColderGjAnnum());
      form.setAverageGjAnnumSingle(solarWaterHeatersApiForm.getAverageGjAnnum());
      form.setWarmerGjAnnumSingle(solarWaterHeatersApiForm.getWarmerGjAnnum());
    } else if (solarWaterHeatersApiForm.getConsumptionUnit().equals(EnergyConsumptionUnit.BOTH.name())) {
      form.setColderKwhAnnumBoth(solarWaterHeatersApiForm.getColderKwhAnnum());
      form.setAverageKwhAnnumBoth(solarWaterHeatersApiForm.getAverageKwhAnnum());
      form.setWarmerKwhAnnumBoth(solarWaterHeatersApiForm.getWarmerKwhAnnum());
      form.setColderGjAnnumBoth(solarWaterHeatersApiForm.getColderGjAnnum());
      form.setAverageGjAnnumBoth(solarWaterHeatersApiForm.getAverageGjAnnum());
      form.setWarmerGjAnnumBoth(solarWaterHeatersApiForm.getWarmerGjAnnum());
    }
    return form;
  }

  public ProcessedEnergyLabelDocument generateHtml(HotWaterStorageTanksForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/hot-water-storage-tanks.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("watts", form.getStandingLoss())
      .setText("litres", form.getVolume())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_STORAGE_TANKS, form);
  }

  public WaterSolarPackagesForm toWaterSolarPackagesForm(WaterSolarPackagesCalculatorForm waterSolarPackagesCalculatorForm) {
    WaterSolarPackagesForm form = new WaterSolarPackagesForm();
    form.setDeclaredLoadProfile(waterSolarPackagesCalculatorForm.getDeclaredLoadProfile());
    form.setHeaterEfficiencyRating(waterSolarPackagesCalculatorService.getWaterHeatingEfficiencyClass(
        waterSolarPackagesCalculatorForm).name());
    form.setPackageEfficiencyRating(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyClass(
        waterSolarPackagesCalculatorForm).name());
    form.setSolarCollector(true);
    form.setStorageTank(waterSolarPackagesCalculatorForm.getStorageTank());
    form.setSupplierName(waterSolarPackagesCalculatorForm.getSupplierName());
    form.setModelName(waterSolarPackagesCalculatorForm.getModelName());
    return form;
  }

  public ProcessedEnergyLabelDocument generateHtml(WaterSolarPackagesForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/packages-of-water-heater-and-solar-device.svg"));

    if (form.getSolarCollector()) {
      templatePopulator.applyCssClassToId("solarCollector", "hasSolarCollector");
    }

    if (form.getStorageTank()) {
      templatePopulator.applyCssClassToId("hotWaterStorageTank", "hasHotWaterStorageTank");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
      .setRatingArrow("rating", RatingClass.getEnum(form.getPackageEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("waterHeatingRatingLetter", RatingClass.getEnum(form.getHeaterEfficiencyRating()).getLetter())
      .setText("waterHeatingRatingPlusses", RatingClass.getEnum(form.getHeaterEfficiencyRating()).getPlusses())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_PACKAGE, form);
  }

  public ProcessedEnergyLabelDocument generateFicheHtml(WaterSolarPackagesCalculatorForm form){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("fiches/water-heaters/packages-of-water-heater-and-solar-device-fiche.svg"));

    String waterHeatingEfficiency = form.getWaterHeatingEfficiencyPercentage();
    String solarContribution = uk.gov.beis.els.util.StringUtils.toPercentage(waterSolarPackagesCalculatorService.getSolarContributionDecimal(form), 1);
    String packageWaterHeatingEfficiency = uk.gov.beis.els.util.StringUtils.toPercentage(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyDecimal(form));

    return templatePopulator
        .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
        .setText("waterHeatingEfficiency1", waterHeatingEfficiency)
        .setText("waterHeatingEfficiency2", waterHeatingEfficiency)
        .setText("waterHeatingEfficiency3", waterHeatingEfficiency)
        .setText("nonSolarScalingFactor", String.format("%.2f", waterSolarPackagesCalculatorService.getNonSolarScalingFactor(form)))
        .setText("auxElectricityConsumptionProportion", uk.gov.beis.els.util.StringUtils.toPercentage(waterSolarPackagesCalculatorService.getAuxElectricityConsumptionProportionDecimal(form), 2))
        .setText("solarContribution1", solarContribution)
        .setText("solarContribution2", solarContribution)
        .setText("solarContribution3", solarContribution)
        .setText("packageWaterHeatingEfficiency", packageWaterHeatingEfficiency)
        .setText("packageWaterHeatingEfficiency2", packageWaterHeatingEfficiency)
        .setText("packageWaterHeatingEfficiency3", packageWaterHeatingEfficiency)
        .setText("packageWaterHeatingEfficiencyColder", uk.gov.beis.els.util.StringUtils.toPercentage(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyColderDecimal(form)))
        .setText("packageWaterHeatingEfficiencyWarmer", uk.gov.beis.els.util.StringUtils.toPercentage(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyWarmerDecimal(form)))
        .applyCssClassToId(LOAD_PROFILE_SVG_IDS.get(LoadProfile.getEnum(form.getDeclaredLoadProfile())), "shown")
        .applyCssClassToId(RATING_CLASS_SVG_IDS.get(waterSolarPackagesCalculatorService.getPackageWaterHeatingEfficiencyClass(form)), "shown")
        .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_PACKAGE, form);
  }

  private void populateClimateConditions(ClimateConditionForm form, TemplatePopulator templatePopulator) {
    EnergyConsumptionUnit unit = EnergyConsumptionUnit.valueOf(form.getConsumptionUnit());

    if (unit == EnergyConsumptionUnit.KWH) {
      templatePopulator
          .setText("colderKwhAnnum", form.getColderKwhAnnumSingle())
          .setText("averageKwhAnnum", form.getAverageKwhAnnumSingle())
          .setText("warmerKwhAnnum", form.getWarmerKwhAnnumSingle())
          .removeElementById("gjGroup");
    } else if (unit == EnergyConsumptionUnit.GJ) {
      templatePopulator
          .setText("colderGjAnnum", form.getColderGjAnnumSingle())
          .setText("averageGjAnnum", form.getAverageGjAnnumSingle())
          .setText("warmerGjAnnum", form.getWarmerGjAnnumSingle())
          .removeElementById("kwhGroup");
    } else {
      templatePopulator
          .setText("colderKwhAnnum", form.getColderKwhAnnumBoth())
          .setText("averageKwhAnnum", form.getAverageKwhAnnumBoth())
          .setText("warmerKwhAnnum", form.getWarmerKwhAnnumBoth())
          .setText("colderGjAnnum", form.getColderGjAnnumBoth())
          .setText("averageGjAnnum", form.getAverageGjAnnumBoth())
          .setText("warmerGjAnnum", form.getWarmerGjAnnumBoth());
    }

  }

}
