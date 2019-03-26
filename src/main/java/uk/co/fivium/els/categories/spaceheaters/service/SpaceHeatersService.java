package uk.co.fivium.els.categories.spaceheaters.service;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.common.LoadProfile;
import uk.co.fivium.els.categories.spaceheaters.model.*;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

import java.util.List;

@Service
public class SpaceHeatersService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_SEP2015 = SelectableLegislationCategory.of(
    "SEP2015",
    "From 26 September 2015",
    RatingClassRange.of(RatingClass.APP, RatingClass.G),
    RatingClassRange.of(RatingClass.A, RatingClass.G));

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_SEP2019 = SelectableLegislationCategory.of(
    "SEP2019",
    "From 26 September 2019",
    RatingClassRange.of(RatingClass.APPP, RatingClass.D),
    RatingClassRange.of(RatingClass.AP, RatingClass.F));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
    .add(LEGISLATION_CATEGORY_SEP2015)
    .add(LEGISLATION_CATEGORY_SEP2019)
    .build();

  public static final LegislationCategory LEGISLATION_CATEGORY_PACKAGES = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G)
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public SpaceHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(BoilerSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_SEP2015) {
      templatePath = "labels/space-heaters/boiler-space-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/boiler-space-heaters-2019.svg";
    }

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getHeatOutput())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(BoilerCombinationHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_SEP2015) {
      templatePath = "labels/space-heaters/boiler-combination-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/boiler-combination-heaters-2019.svg";
    }

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));

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
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("spaceHeatingRating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange(), "data-rating-increment-space-heating")
      .setRatingArrow("waterHeatingRating", RatingClass.valueOf(form.getWaterHeatingEfficiencyRating()), legislationCategory.getSecondaryRatingRange(), "data-rating-increment-water-heating")
      .getPopulatedDocument();
  }

  public Document generateHtml(CogenerationSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_SEP2015) {
      templatePath = "labels/space-heaters/cogeneration-space-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/cogeneration-space-heaters-2019.svg";
    }

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));

    if (form.getElectricityGeneration()) {
      templatePopulator.applyCssClassToId("additionalElectricityGeneration", "hasAdditionalElectricityGeneration");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getHeatOutput())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(LowTemperatureHeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_SEP2015) {
      templatePath = "labels/space-heaters/low-temperature-heat-pump-space-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/low-temperature-heat-pump-space-heaters-2019.svg";
    }
      return generateHtml(templatePath, form, legislationCategory, null, null, null, null);
  }

  public Document generateHtml(HeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_SEP2015) {
      templatePath = "labels/space-heaters/heat-pump-space-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/heat-pump-space-heaters-2019.svg";
    }
    return generateHtml(templatePath, form, legislationCategory, form.getMediumTempEfficiencyRating(), form.getMediumTempColderHeatOutput(), form.getMediumTempAverageHeatOutput(), form.getMediumTempWarmerHeatOutput());
  }

  private Document generateHtml(String templatePath, LowTemperatureHeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory, String mediumTempEfficiencyRating, String mediumTempColderHeatOutput, String mediumTempAverageHeatOutput, String mediumTempWarmerHeatOutput) {
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
      templatePopulator.setRatingArrow("mediumTemperatureRating", RatingClass.valueOf(mediumTempEfficiencyRating), legislationCategory.getPrimaryRatingRange());
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
      .setRatingArrow("lowTemperatureRating", RatingClass.valueOf(form.getLowTempEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("lowTemperatureColderKw", form.getLowTempColderHeatOutput())
      .setText("lowTemperatureAverageKw", form.getLowTempAverageHeatOutput())
      .setText("lowTemperatureWarmerKw", form.getLowTempWarmerHeatOutput())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(HeatPumpCombinationHeatersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;
    if (legislationCategory == LEGISLATION_CATEGORY_SEP2015) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/heat-pump-combination-heaters-2015.svg"));
    }
    else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/space-heaters/heat-pump-combination-heaters-2019.svg"));
    }

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
      .setRatingArrow("spaceHeatingRating", RatingClass.valueOf(form.getSpaceHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange(), "data-rating-increment-space-heating")
      .setRatingArrow("waterHeatingRating", RatingClass.valueOf(form.getWaterHeatingEfficiencyRating()), legislationCategory.getSecondaryRatingRange(), "data-rating-increment-water-heating")
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("colderKw", form.getColderHeatOutput())
      .setText("averageKw", form.getAverageHeatOutput())
      .setText("warmerKw", form.getWarmerHeatOutput())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(SpaceHeaterPackagesForm form) {
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
      .setRatingArrow("rating", RatingClass.valueOf(form.getPackageEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange())
      .setText("boilerRatingLetter", RatingClass.valueOf(form.getHeaterEfficiencyRating()).getLetter())
      .setText("boilerRatingPlusses", RatingClass.valueOf(form.getHeaterEfficiencyRating()).getPlusses())
      .getPopulatedDocument();
  }

  public Document generateHtml(CombinationHeaterPackagesForm form) {
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
      .setRatingArrow("packageSpaceHeatingRating", RatingClass.valueOf(form.getPackageSpaceHeatingEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange())
      .setRatingArrow("packageWaterHeatingRating", RatingClass.valueOf(form.getPackageWaterHeatingEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES.getPrimaryRatingRange())
      .setText("heaterSpaceHeatingRatingLetter", RatingClass.valueOf(form.getSpaceHeaterEfficiencyRating()).getLetter())
      .setText("heaterSpaceHeatingRatingPlusses", RatingClass.valueOf(form.getSpaceHeaterEfficiencyRating()).getPlusses())
      .setText("heaterWaterHeatingRatingLetter", RatingClass.valueOf(form.getWaterHeaterEfficiencyRating()).getLetter())
      .setText("heaterWaterHeatingRatingPlusses", RatingClass.valueOf(form.getWaterHeaterEfficiencyRating()).getPlusses())
      .setText("heaterDeclaredLoadProfile", LoadProfile.valueOf(form.getHeaterDeclaredLoadProfile()).getDisplayName())
      .setText("packageDeclaredLoadProfile", LoadProfile.valueOf(form.getPackageDeclaredLoadProfile()).getDisplayName())
      .getPopulatedDocument();
  }

}
