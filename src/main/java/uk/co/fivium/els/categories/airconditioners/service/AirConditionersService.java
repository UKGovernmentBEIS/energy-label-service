package uk.co.fivium.els.categories.airconditioners.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.airconditioners.model.*;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class AirConditionersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_JAN2019 = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  private final TemplateParserService templateParserService;

  @Autowired
  public AirConditionersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(CoolingDuctlessAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/non-duct/cooling-only-air-conditioners.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getCoolingModeDesignLoad())
      .setText("seer", form.getCoolingModeSeer())
      .setText("kwhAnnum", form.getCoolingAnnualEnergyConsumption())
      .setRatingArrow("rating", RatingClass.valueOf(form.getCoolingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("insideDb", form.getSoundPowerLevelIndoors())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(HeatingDuctlessAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/non-duct/heating-only-air-conditioners.svg"));

    applyMultipleClimateSection(templatePopulator, form, legislationCategory);

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("averageKw", form.getAverageHeatingDesignLoad())
      .setText("averageScop", form.getAverageScop())
      .setText("averageKwhAnnum", form.getAverageAnnualEnergyConsumption())
      .setRatingArrow("averageRating", RatingClass.valueOf(form.getAverageHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("insideDb", form.getSoundPowerLevelIndoors())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(ReversibleDuctlessAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/non-duct/reversible-air-conditioners.svg"));

    applyMultipleClimateSection(templatePopulator, form, legislationCategory);

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("seerKw", form.getCoolingModeDesignLoad())
      .setText("seer", form.getCoolingModeSeer())
      .setText("seerKwhAnnum", form.getCoolingAnnualEnergyConsumption())
      .setRatingArrow("seerRating", RatingClass.valueOf(form.getCoolingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("averageScopKw", form.getAverageHeatingDesignLoad())
      .setText("averageScop", form.getAverageScop())
      .setText("averageScopKwhAnnum", form.getAverageAnnualEnergyConsumption())
      .setRatingArrow("averageScopRating", RatingClass.valueOf(form.getAverageHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("insideDb", form.getSoundPowerLevelIndoors())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(CoolingDuctedAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/ducted/cooling-only-single-or-double-duct-air-conditioners.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getCoolingKw())
      .setText("eer", form.getEerRated())
      .setText("kwhHour", form.getCoolingHourlyEnergyConsumption())
      .setRatingArrow("rating", RatingClass.valueOf(form.getCoolingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("db", form.getSoundPowerLevelIndoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(HeatingDuctedAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/ducted/heating-only-single-or-double-duct-air-conditioners.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getHeatingKw())
      .setText("cop", form.getCopRated())
      .setText("kwhHour", form.getHeatingHourlyEnergyConsumption())
      .setRatingArrow("rating", RatingClass.valueOf(form.getHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("db", form.getSoundPowerLevelIndoors())
      .getPopulatedDocument();
  }

  public Document generateHtml(ReversibleDuctedAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/ducted/reversible-single-or-double-duct-air-conditioners.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("copKw", form.getHeatingKw())
      .setText("cop", form.getCopRated())
      .setText("copKwhHour", form.getHeatingHourlyEnergyConsumption())
      .setRatingArrow("copRating", RatingClass.valueOf(form.getHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("eerKw", form.getCoolingKw())
      .setText("eer", form.getEerRated())
      .setText("eerKwhHour", form.getCoolingHourlyEnergyConsumption())
      .setRatingArrow("eerRating", RatingClass.valueOf(form.getCoolingEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("db", form.getSoundPowerLevelIndoors())
      .getPopulatedDocument();
  }

  private void applyMultipleClimateSection(TemplatePopulator templatePopulator, MultipleClimateGroupForm form, LegislationCategory legislationCategory) {
    if (form.getColderConditions()) {
      templatePopulator
          .setText("colderScopKw", form.getColderHeatingDesignLoad())
          .setText("colderScop", form.getColderScop())
          .setText("colderScopKwhAnnum", form.getColderAnnualEnergyConsumption())
          .setRatingArrow("colderScopRating", RatingClass.valueOf(form.getColderHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange());
    }
    else {
      templatePopulator.removeElementById("colderScopRating");
    }

    if (form.getWarmerConditions()) {
      templatePopulator
          .setText("warmerScopKw", form.getWarmerHeatingDesignLoad())
          .setText("warmerScop", form.getWarmerScop())
          .setText("warmerScopKwhAnnum", form.getWarmerAnnualEnergyConsumption())
          .setRatingArrow("warmerScopRating", RatingClass.valueOf(form.getWarmerHeatingEfficiencyRating()), legislationCategory.getPrimaryRatingRange());
    }
    else {
      templatePopulator.removeElementById("warmerScopRating");
    }
  }

}
