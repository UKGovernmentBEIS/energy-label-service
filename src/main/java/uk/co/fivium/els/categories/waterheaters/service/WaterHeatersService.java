package uk.co.fivium.els.categories.waterheaters.service;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.waterheaters.model.*;
import uk.co.fivium.els.categories.common.LoadProfile;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class WaterHeatersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.AP, RatingClass.F));

  public static final LegislationCategory LEGISLATION_CATEGORY_SOLAR_PACKAGES = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G),
    RatingClassRange.of(RatingClass.AP, RatingClass.G)
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public WaterHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(HeatPumpWaterHeatersForm form, LegislationCategory legislationCategory){
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

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("colderKwhAnnum", form.getColderKwhAnnum())
      .setText("averageKwhAnnum", form.getAverageKwhAnnum())
      .setText("warmerKwhAnnum", form.getWarmerKwhAnnum())
      .setText("colderGjAnnum", form.getColderGjAnnum())
      .setText("averageGjAnnum", form.getAverageGjAnnum())
      .setText("warmerGjAnnum", form.getWarmerGjAnnum())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(ConventionalWaterHeatersForm form, LegislationCategory legislationCategory){
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
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(SolarWaterHeatersForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/solar-water-heaters.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("colderKwhAnnum", form.getColderKwhAnnum())
      .setText("averageKwhAnnum", form.getAverageKwhAnnum())
      .setText("warmerKwhAnnum", form.getWarmerKwhAnnum())
      .setText("colderGjAnnum", form.getColderGjAnnum())
      .setText("averageGjAnnum", form.getAverageGjAnnum())
      .setText("warmerGjAnnum", form.getWarmerGjAnnum())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(HotWaterStorageTanksForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/hot-water-storage-tanks.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("watts", form.getStandingLoss())
      .setText("litres", form.getVolume())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(WaterSolarPackagesForm form, LegislationCategory legislationCategory){
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
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setRatingArrow("rating", RatingClass.valueOf(form.getPackageEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("waterHeatingRatingLetter", RatingClass.valueOf(form.getHeaterEfficiencyRating()).getLetter())
      .setText("waterHeatingRatingPlusses", RatingClass.valueOf(form.getHeaterEfficiencyRating()).getPlusses())
      .getPopulatedDocument();
  }
}
