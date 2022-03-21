package uk.gov.beis.els.categories.spaceheaters.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.BoilerSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CogenerationPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.CogenerationSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.CombinationHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpCombinationHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.HeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesCalculatorForm;
import uk.gov.beis.els.categories.spaceheaters.model.SpaceHeaterPackagesForm;
import uk.gov.beis.els.categories.spaceheaters.model.TankLabelClass;
import uk.gov.beis.els.categories.waterheaters.model.WaterSolarPackagesForm;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class SpaceHeatersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
          RatingClassRange.of(RatingClass.APPP, RatingClass.D),
          RatingClassRange.of(RatingClass.AP, RatingClass.F)); // hot water efficiency

  public static final LegislationCategory LEGISLATION_CATEGORY_PACKAGES = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G)
  );
  private static final Map<RatingClass, String> RATING_CLASS_SVG_IDS;

  static {
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
  private final SpaceHeaterPackagesCalculatorService spaceHeaterPackagesCalculatorService;
  private final WaterHeatersService waterHeatersService;

  @Autowired
  public SpaceHeatersService(TemplateParserService templateParserService,
                             SpaceHeaterPackagesCalculatorService spaceHeaterPackagesCalculatorService,
                             WaterHeatersService waterHeatersService) {
    this.templateParserService = templateParserService;
    this.spaceHeaterPackagesCalculatorService = spaceHeaterPackagesCalculatorService;
    this.waterHeatersService = waterHeatersService;
  }

  public ProcessedEnergyLabelDocument generateHtml(BoilerSpaceHeatersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/boiler-space-heaters.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getHeatOutput())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_BOILER, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(BoilerCombinationHeatersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/boiler-combination-heaters.svg"));

    if (form.getOffPeak()) {
      templatePopulator.applyCssClassToId("offPeakMode", "hasOffPeakMode");
    }

    if (StringUtils.isNotBlank(form.getSoundPowerLevelIndoors())) {
      templatePopulator
        .applyCssClassToId("dbSection", "hasDbSection")
        .setText("db", form.getSoundPowerLevelIndoors());
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getHeatOutput())
      .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
      .setRatingArrow("spaceHeatingRating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange(), "data-rating-increment-space-heating")
      .setRatingArrow("waterHeatingRating", RatingClass.getEnum(form.getWaterHeatingEfficiencyRating()), legislationCategory.getSecondaryRatingRange(), "data-rating-increment-water-heating")
      .asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_BOILER_COMBI, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(CogenerationSpaceHeatersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/cogeneration-space-heaters.svg"));

    if (form.getElectricityGeneration()) {
      templatePopulator.applyCssClassToId("additionalElectricityGeneration", "hasAdditionalElectricityGeneration");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getHeatOutput())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_COGEN, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(LowTemperatureHeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath = "labels/space-heaters/low-temperature-heat-pump-space-heaters.svg";
    return generateHtml(templatePath, form, legislationCategory, ProductMetadata.SPACE_HEATER_LOW_TEMP, null, null, null, null);
  }

  public ProcessedEnergyLabelDocument generateHtml(HeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath = "labels/space-heaters/heat-pump-space-heaters.svg";
    return generateHtml(templatePath, form, legislationCategory, ProductMetadata.SPACE_HEATER_HEAT_PUMP, form.getMediumTempEfficiencyRating(), form.getMediumTempColderHeatOutput(), form.getMediumTempAverageHeatOutput(), form.getMediumTempWarmerHeatOutput());
  }

  private ProcessedEnergyLabelDocument generateHtml(String templatePath, LowTemperatureHeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory, ProductMetadata productMetadata, String mediumTempEfficiencyRating, String mediumTempColderHeatOutput, String mediumTempAverageHeatOutput, String mediumTempWarmerHeatOutput) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));
    if (StringUtils.isNotBlank(form.getSoundPowerLevelIndoors())) {
      templatePopulator
        .applyCssClassToId("insideDbSection", "hasInsideDb")
        .setText("insideDb", form.getSoundPowerLevelIndoors());
    }
    else {
      templatePopulator.removeElementById("insideDbSection");
    }

    if (StringUtils.isNotBlank(mediumTempEfficiencyRating)) {
      templatePopulator.setRatingArrow("mediumTemperatureRating", RatingClass.getEnum(mediumTempEfficiencyRating), legislationCategory.getPrimaryRatingRange());
    }
    if (StringUtils.isNotBlank(mediumTempColderHeatOutput)) {
      templatePopulator.setText("mediumTemperatureColderKw", mediumTempColderHeatOutput);
    }
    if (StringUtils.isNotBlank(mediumTempAverageHeatOutput)) {
      templatePopulator.setText("mediumTemperatureAverageKw", mediumTempAverageHeatOutput);
    }
    if (StringUtils.isNotBlank(mediumTempWarmerHeatOutput)) {
      templatePopulator.setText("mediumTemperatureWarmerKw", mediumTempWarmerHeatOutput);
    }


    return templatePopulator
      .setRatingArrow("lowTemperatureRating", RatingClass.getEnum(form.getLowTempEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("lowTemperatureColderKw", form.getLowTempColderHeatOutput())
      .setText("lowTemperatureAverageKw", form.getLowTempAverageHeatOutput())
      .setText("lowTemperatureWarmerKw", form.getLowTempWarmerHeatOutput())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .asProcessedEnergyLabel(productMetadata, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(HeatPumpCombinationHeatersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/heat-pump-combination-heaters.svg"));

    if (StringUtils.isNotBlank(form.getSoundPowerLevelIndoors())) {
      templatePopulator
        .applyCssClassToId("insideDbSection", "hasInsideDb")
        .setText("insideDb", form.getSoundPowerLevelIndoors());
    }
    else {
      templatePopulator.removeElementById("insideDbSection");
    }

    if (!form.getOffPeak()) {
      templatePopulator
        .removeElementById("offPeakMode");
    }

    return templatePopulator
      .setRatingArrow("spaceHeatingRating", RatingClass.getEnum(form.getSpaceHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange(), "data-rating-increment-space-heating")
      .setRatingArrow("waterHeatingRating", RatingClass.getEnum(form.getWaterHeatingEfficiencyRating()), legislationCategory.getSecondaryRatingRange(), "data-rating-increment-water-heating")
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.getEnum(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("colderKw", form.getColderHeatOutput())
      .setText("averageKw", form.getAverageHeatOutput())
      .setText("warmerKw", form.getWarmerHeatOutput())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_HEAT_PUMP_COMBINATION, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(SpaceHeaterPackagesForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/packages-of-space-heater-temperature-control-and-solar-device.svg"));

    if (form.getSolarCollector()) {
      templatePopulator.applyCssClassToId("solarCollector", "hasSolarCollector");
    }
    if (form.getHotWaterStorageTank()) {
      templatePopulator.applyCssClassToId("hotWaterStorageTank", "hasHotWaterStorageTank");
    }
    if (form.getTemperatureControl()) {
      templatePopulator.applyCssClassToId("temperatureControl", "hasTemperatureControl");
    }
    if (form.getSpaceHeater()) {
      templatePopulator.applyCssClassToId("supplementarySpaceHeater", "hasSupplementarySpaceHeater");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("rating", RatingClass.getEnum(form.getPackageEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange())
      .setText("boilerRatingLetter", RatingClass.getEnum(form.getHeaterEfficiencyRating()).getLetter())
      .setText("boilerRatingPlusses", RatingClass.getEnum(form.getHeaterEfficiencyRating()).getPlusses())
      .asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_PACKAGE, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(CombinationHeaterPackagesForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/packages-of-combination-heater-temperature-control-and-solar-device.svg"));

    if (form.getSolarCollector()) {
      templatePopulator.applyCssClassToId("solarCollector", "hasSolarCollector");
    }
    if (form.getHotWaterStorageTank()) {
      templatePopulator.applyCssClassToId("hotWaterStorageTank", "hasHotWaterStorageTank");
    }
    if (form.getTemperatureControl()) {
      templatePopulator.applyCssClassToId("temperatureControl", "hasTemperatureControl");
    }
    if (form.getSpaceHeater()) {
      templatePopulator.applyCssClassToId("supplementarySpaceHeater", "hasSupplementarySpaceHeater");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("packageSpaceHeatingRating", RatingClass.getEnum(form.getPackageSpaceHeatingEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange())
      .setRatingArrow("packageWaterHeatingRating", RatingClass.getEnum(form.getPackageWaterHeatingEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange())
      .setText("heaterSpaceHeatingRatingLetter", RatingClass.getEnum(form.getSpaceHeaterEfficiencyRating()).getLetter())
      .setText("heaterSpaceHeatingRatingPlusses", RatingClass.getEnum(form.getSpaceHeaterEfficiencyRating()).getPlusses())
      .setText("heaterWaterHeatingRatingLetter", RatingClass.getEnum(form.getWaterHeaterEfficiencyRating()).getLetter())
      .setText("heaterWaterHeatingRatingPlusses", RatingClass.getEnum(form.getWaterHeaterEfficiencyRating()).getPlusses())
      .setText("heaterDeclaredLoadProfile", LoadProfile.getEnum(form.getHeaterDeclaredLoadProfile()).getDisplayName())
      .setText("packageDeclaredLoadProfile", LoadProfile.getEnum(form.getPackageDeclaredLoadProfile()).getDisplayName())
      .asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_PACKAGE_COMBINATION, form);
  }

  public ProcessedEnergyLabelDocument generateFicheHtml(BoilerPackagesCalculatorForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(
        templateParserService.parseTemplate("fiches/space-heaters/boilerSpaceHeaterPackages.svg"));
    float preferentialHeaterSeasonalSpaceHeatingEfficiency =
        spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form);
    float solarContribution = spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form);
    String supplementaryHeatPumpFactor =
        String.format("%.2f", spaceHeaterPackagesCalculatorService.getSupplementaryHeatPumpFactor(form));
    String supplementaryHeatPumpContribution = uk.gov.beis.els.util.StringUtils.toPercentage(
        spaceHeaterPackagesCalculatorService.getSupplementaryHeatPumpContributionDecimal(form)
        ,1
    );
    float packageSpaceHeatingEfficiency =
        spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(form);

    templatePopulator
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency1",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 1))
        .setText("temperatureControlEfficiency", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form), 1))
        .setText("supplementaryBoilerSeasonalSpaceHeatingEfficiency", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyDecimal(form), 2))
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency2",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 2))
        .setText("supplementaryBoilerContribution", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form), 1))
        .setText("solarCollectorSizeFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getSolarCollectorSizeFactor(form)))
        .setText("tankVolumeFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getTankVolumeFactor(form)))
        .setText("storageTankVolumeMetersCubed",
            String.format("%.3f", spaceHeaterPackagesCalculatorService.getStorageTankVolumeMetersCubed(form)))
        .setText("solarContribution1", uk.gov.beis.els.util.StringUtils.toPercentage(solarContribution, 1))
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency3",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 2))
        .setText("supplementaryHeatPumpFactor1", supplementaryHeatPumpFactor)
        .setText("supplementaryHeatPumpContribution1", supplementaryHeatPumpContribution)
        .setText("solarContribution2", uk.gov.beis.els.util.StringUtils.toPercentage(solarContribution, 2))
        .setText("supplementaryHeatPumpContribution2", supplementaryHeatPumpContribution)
        .setText("solarContributionAndHeatPump", uk.gov.beis.els.util.StringUtils.toPercentage(
            Math.abs(spaceHeaterPackagesCalculatorService.getSolarContributionAndHeatPumpDecimal(form)), 1))
        .setText("packageSpaceHeatingEfficiency1",
            uk.gov.beis.els.util.StringUtils.toPercentage(packageSpaceHeatingEfficiency))
        .setText("solarCollectorSize", String.format("%.2f", Float.parseFloat(form.getSolarCollectorSize())))
        .setText("solarCollectorEfficiencyPercentage",
            String.format("%.2f", Float.parseFloat(form.getSolarCollectorEfficiencyPercentage())))
        .applyCssClassToId(
            RATING_CLASS_SVG_IDS.get(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)),
            "shown");

    if (form.getHasStorageTank()) {
      templatePopulator
          .setText("storageTankRating",
              String.format("%.2f", TankLabelClass.getEnum(form.getStorageTankRating()).getRatingValue()));
    } else {
      templatePopulator
          .setText("storageTankRating", "");
    }

    if (form.getHasSupplementaryHeatPump()) {
      templatePopulator
          .setText("supplementaryHeatPumpSeasonalSpaceHeatingEfficiency", String.format("%.2f",
              Float.parseFloat(form.getSupplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage())))
          .setText("packageSpaceHeatingEfficiency2",
              uk.gov.beis.els.util.StringUtils.toPercentage(packageSpaceHeatingEfficiency, 2))
          .setText("supplementaryHeatPumpFactor2", supplementaryHeatPumpFactor)
          .setText("lowTemperatureHeatEmitters", uk.gov.beis.els.util.StringUtils.toPercentage(
              spaceHeaterPackagesCalculatorService.getLowTemperatureHeatEmitters(form)));
    } else {
      templatePopulator
          .setText("supplementaryHeatPumpSeasonalSpaceHeatingEfficiency", "0.00")
          .setText("packageSpaceHeatingEfficiency2","")
          .setText("supplementaryHeatPumpFactor2", "")
          .setText("lowTemperatureHeatEmitters", "");
    }

    return templatePopulator.asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_PACKAGE, form);
  }

  public ProcessedEnergyLabelDocument generateFicheHtml(CogenerationPackagesCalculatorForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(
        templateParserService.parseTemplate("fiches/space-heaters/cogenerationSpaceHeaterPackages.svg"));
    float preferentialHeaterSeasonalSpaceHeatingEfficiency =
        spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(form);

    templatePopulator
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency1",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 1))
        .setText("temperatureControlEfficiency", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form), 1))
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency2",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 2))
        .setText("supplementaryBoilerFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getSupplementaryBoilerFactor(form)))
        .setText("supplementaryBoilerContribution", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form), 1))
        .setText("solarCollectorSizeFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getSolarCollectorSizeFactor(form)))
        .setText("tankVolumeFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getTankVolumeFactor(form)))
        .setText("storageTankVolumeMetersCubed",
            String.format("%.3f", spaceHeaterPackagesCalculatorService.getStorageTankVolumeMetersCubed(form)))
        .setText("solarContribution", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form), 1))
        .setText("packageSpaceHeatingEfficiency", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(form)))
        .setText("solarCollectorSize", String.format("%.2f", Float.parseFloat(form.getSolarCollectorSize())))
        .setText("solarCollectorEfficiencyPercentage",
            String.format("%.2f", Float.parseFloat(form.getSolarCollectorEfficiencyPercentage())))
        .applyCssClassToId(
            RATING_CLASS_SVG_IDS.get(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)),
            "shown");

    if (form.getHasSupplementaryBoiler()) {
      templatePopulator
          .setText("supplementaryBoilerSeasonalSpaceHeatingEfficiency", String.format("%.2f",
              Float.parseFloat(form.getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage())));
    } else {
      templatePopulator
          .setText("supplementaryBoilerSeasonalSpaceHeatingEfficiency", "0.00");
    }

    if (form.getHasStorageTank()) {
      templatePopulator
          .setText("storageTankRating",
              String.format("%.2f", TankLabelClass.getEnum(form.getStorageTankRating()).getRatingValue()));
    } else {
      templatePopulator
          .setText("storageTankRating", "");
    }

    return templatePopulator.asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_PACKAGE, form);
  }

  public ProcessedEnergyLabelDocument generateFicheHtml(HeatPumpPackagesCalculatorForm form) {
    TemplatePopulator templatePopulator;
    if (form.getLowTemperatureHeatPump()) {
      templatePopulator = new TemplatePopulator(
          templateParserService.parseTemplate("fiches/space-heaters/lowTemperatureHeatPumpSpaceHeaterPackages.svg"));
    } else {
      templatePopulator = new TemplatePopulator(
          templateParserService.parseTemplate("fiches/space-heaters/heatPumpSpaceHeaterPackages.svg"));
    }

    float preferentialHeaterSeasonalSpaceHeatingEfficiency = spaceHeaterPackagesCalculatorService.getPreferentialHeaterSeasonalSpaceHeatingEfficiencyDecimal(
        form);
    float packageSpaceHeatingEfficiency = spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyDecimal(
        form);
    float preferentialHeatPumpWarmerDifference = spaceHeaterPackagesCalculatorService.getPreferentialHeatPumpWarmerDifferenceDecimal(form);
    float preferentialHeatPumpColderDifference = spaceHeaterPackagesCalculatorService.getPreferentialHeatPumpColderDifferenceDecimal(form);

    templatePopulator
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency1",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 1))
        .setText("temperatureControlEfficiency", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getTemperatureControlEfficiencyDecimal(form), 1))
        .setText("preferentialHeaterSeasonalSpaceHeatingEfficiency2",
            uk.gov.beis.els.util.StringUtils.toPercentage(preferentialHeaterSeasonalSpaceHeatingEfficiency, 2))
        .setText("supplementaryBoilerFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getSupplementaryBoilerFactor(form)))
        .setText("supplementaryBoilerContribution", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getSupplementaryBoilerContributionDecimal(form), 1))
        .setText("solarCollectorSizeFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getSolarCollectorSizeFactor(form)))
        .setText("tankVolumeFactor",
            String.format("%.2f", spaceHeaterPackagesCalculatorService.getTankVolumeFactor(form)))
        .setText("storageTankVolumeMetersCubed",
            String.format("%.3f", spaceHeaterPackagesCalculatorService.getStorageTankVolumeMetersCubed(form)))
        .setText("solarContribution", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getSolarContributionDecimal(form), 1))
        .setText("packageSpaceHeatingEfficiency1",
            uk.gov.beis.els.util.StringUtils.toPercentage(packageSpaceHeatingEfficiency))
        .setText("packageSpaceHeatingEfficiency2",
            uk.gov.beis.els.util.StringUtils.toPercentage(packageSpaceHeatingEfficiency, 2))
        .setText("packageSpaceHeatingEfficiency3",
            uk.gov.beis.els.util.StringUtils.toPercentage(packageSpaceHeatingEfficiency, 2))
        .setText("preferentialHeatPumpColderDifference", uk.gov.beis.els.util.StringUtils.toPercentage(
            Math.abs(preferentialHeatPumpColderDifference), 2))
        .setText("preferentialHeatPumpWarmerDifference", uk.gov.beis.els.util.StringUtils.toPercentage(
            Math.abs(preferentialHeatPumpWarmerDifference), 2))
        .setText("packageSpaceHeatingEfficiencyColder", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyColderDecimal(form)))
        .setText("packageSpaceHeatingEfficiencyWarmer", uk.gov.beis.els.util.StringUtils.toPercentage(
            spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyWarmerDecimal(form)))
        .setText("solarCollectorEfficiencyPercentage",
            String.format("%.2f", Float.parseFloat(form.getSolarCollectorEfficiencyPercentage())))
        .setText("solarCollectorSize", String.format("%.2f", Float.parseFloat(form.getSolarCollectorSize())))
        .applyCssClassToId(
            RATING_CLASS_SVG_IDS.get(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(form)),
            "shown");

    if (form.getHasSupplementaryBoiler()) {
      templatePopulator
          .setText("supplementaryBoilerSeasonalSpaceHeatingEfficiency", String.format("%.2f",
              Float.parseFloat(form.getSupplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage())));
    } else {
      templatePopulator
          .setText("supplementaryBoilerSeasonalSpaceHeatingEfficiency", "0.00");
    }

    if (form.getHasStorageTank()) {
      templatePopulator
          .setText("storageTankRating",
              String.format("%.2f", TankLabelClass.getEnum(form.getStorageTankRating()).getRatingValue()));
    } else {
      templatePopulator
          .setText("storageTankRating", "");
    }

    if (preferentialHeatPumpColderDifference >= 0) {
      templatePopulator.setText("signColder", "+");
    } else {
      templatePopulator.setText("signColder", "-");
    }

    if (preferentialHeatPumpWarmerDifference >= 0) {
      templatePopulator.setText("signWarmer", "+");
    } else {
      templatePopulator.setText("signWarmer", "-");
    }

    return templatePopulator.asProcessedEnergyLabel(ProductMetadata.SPACE_HEATER_PACKAGE, form);
  }

  public SpaceHeaterPackagesForm toSpaceHeaterPackagesForm(
      SpaceHeaterPackagesCalculatorForm calculatorForm) {
    SpaceHeaterPackagesForm form = new SpaceHeaterPackagesForm();
    form.setHeaterEfficiencyRating(spaceHeaterPackagesCalculatorService.gePreferentialHeaterEfficiencyClass(calculatorForm).name());
    form.setSolarCollector(true);
    form.setHotWaterStorageTank(calculatorForm.getHasStorageTank());
    form.setTemperatureControl(calculatorForm.getHasTemperatureControl());
    form.setSpaceHeater(calculatorForm.getSpaceHeater());
    form.setPackageEfficiencyRating(spaceHeaterPackagesCalculatorService.getPackageSpaceHeatingEfficiencyClass(calculatorForm).name());
    form.setSupplierName(calculatorForm.getSupplierName());
    form.setModelName(calculatorForm.getModelName());

    return form;
  }

  public CombinationHeaterPackagesForm toCombinationHeaterPackagesForm(BoilerCombinationCalculatorForm boilerCombinationCalculatorForm) {
    CombinationHeaterPackagesForm combinationHeaterPackagesForm = new CombinationHeaterPackagesForm();
    WaterSolarPackagesForm waterSolarForm = waterHeatersService.toWaterSolarPackagesForm(boilerCombinationCalculatorForm);
    SpaceHeaterPackagesForm spaceHeaterPackagesForm = toSpaceHeaterPackagesForm(boilerCombinationCalculatorForm);

    combinationHeaterPackagesForm.setSpaceHeaterEfficiencyRating(spaceHeaterPackagesForm.getHeaterEfficiencyRating());
    combinationHeaterPackagesForm.setWaterHeaterEfficiencyRating(waterSolarForm.getHeaterEfficiencyRating());
    combinationHeaterPackagesForm.setHeaterDeclaredLoadProfile(boilerCombinationCalculatorForm.getDeclaredLoadProfile());
    combinationHeaterPackagesForm.setSolarCollector(true);
    combinationHeaterPackagesForm.setHotWaterStorageTank(boilerCombinationCalculatorForm.getHasStorageTank());
    combinationHeaterPackagesForm.setTemperatureControl(boilerCombinationCalculatorForm.getHasTemperatureControl());
    combinationHeaterPackagesForm.setSpaceHeater(boilerCombinationCalculatorForm.getSpaceHeater());
    combinationHeaterPackagesForm.setPackageSpaceHeatingEfficiencyRating(spaceHeaterPackagesForm.getPackageEfficiencyRating());
    combinationHeaterPackagesForm.setPackageWaterHeatingEfficiencyRating(waterSolarForm.getPackageEfficiencyRating());
    combinationHeaterPackagesForm.setPackageDeclaredLoadProfile(boilerCombinationCalculatorForm.getDeclaredLoadProfile());
    combinationHeaterPackagesForm.setSupplierName(boilerCombinationCalculatorForm.getSupplierName());
    combinationHeaterPackagesForm.setModelName(boilerCombinationCalculatorForm.getModelName());

    return combinationHeaterPackagesForm;
  }

  public CombinationHeaterPackagesForm toCombinationHeaterPackagesForm(
      HeatPumpCombinationCalculatorForm heatPumpCombinationCalculatorForm) {
    CombinationHeaterPackagesForm combinationHeaterPackagesForm = new CombinationHeaterPackagesForm();
    WaterSolarPackagesForm waterSolarForm = waterHeatersService.toWaterSolarPackagesForm(heatPumpCombinationCalculatorForm);
    SpaceHeaterPackagesForm spaceHeaterPackagesForm = toSpaceHeaterPackagesForm(heatPumpCombinationCalculatorForm);

    combinationHeaterPackagesForm.setSpaceHeaterEfficiencyRating(spaceHeaterPackagesForm.getHeaterEfficiencyRating());
    combinationHeaterPackagesForm.setWaterHeaterEfficiencyRating(waterSolarForm.getHeaterEfficiencyRating());
    combinationHeaterPackagesForm.setHeaterDeclaredLoadProfile(heatPumpCombinationCalculatorForm.getDeclaredLoadProfile());
    combinationHeaterPackagesForm.setSolarCollector(true);
    combinationHeaterPackagesForm.setHotWaterStorageTank(heatPumpCombinationCalculatorForm.getHasStorageTank());
    combinationHeaterPackagesForm.setTemperatureControl(heatPumpCombinationCalculatorForm.getHasTemperatureControl());
    combinationHeaterPackagesForm.setSpaceHeater(heatPumpCombinationCalculatorForm.getSpaceHeater());
    combinationHeaterPackagesForm.setPackageSpaceHeatingEfficiencyRating(spaceHeaterPackagesForm.getPackageEfficiencyRating());
    combinationHeaterPackagesForm.setPackageWaterHeatingEfficiencyRating(waterSolarForm.getPackageEfficiencyRating());
    combinationHeaterPackagesForm.setPackageDeclaredLoadProfile(heatPumpCombinationCalculatorForm.getDeclaredLoadProfile());
    combinationHeaterPackagesForm.setSupplierName(heatPumpCombinationCalculatorForm.getSupplierName());
    combinationHeaterPackagesForm.setModelName(heatPumpCombinationCalculatorForm.getModelName());

    return combinationHeaterPackagesForm;
  }
}
