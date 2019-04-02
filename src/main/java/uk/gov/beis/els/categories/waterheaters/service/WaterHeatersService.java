package uk.gov.beis.els.categories.waterheaters.service;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.waterheaters.model.ClimateConditionForm;
import uk.gov.beis.els.categories.waterheaters.model.ConventionalWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.gov.beis.els.categories.waterheaters.model.HotWaterStorageTanksForm;
import uk.gov.beis.els.categories.waterheaters.model.SolarWaterHeatersForm;
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

  private final TemplateParserService templateParserService;

  @Autowired
  public WaterHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
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
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_HEAT_PUMP, form);
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
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_CONVENTIONAL, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(SolarWaterHeatersForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/solar-water-heaters.svg"));

    populateClimateConditions(form, templatePopulator);

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("db", form.getSoundPowerLevelIndoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_SOLAR, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(HotWaterStorageTanksForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/hot-water-storage-tanks.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("watts", form.getStandingLoss())
      .setText("litres", form.getVolume())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.WATER_HEATERS_STORAGE_TANKS, form);
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
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setRatingArrow("rating", RatingClass.valueOf(form.getPackageEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("waterHeatingRatingLetter", RatingClass.valueOf(form.getHeaterEfficiencyRating()).getLetter())
      .setText("waterHeatingRatingPlusses", RatingClass.valueOf(form.getHeaterEfficiencyRating()).getPlusses())
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
