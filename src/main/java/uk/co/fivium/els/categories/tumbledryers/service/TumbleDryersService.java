package uk.co.fivium.els.categories.tumbledryers.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryerSubCategory;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.model.CondenserTumbleDryersForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class TumbleDryersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.D),
    RatingClassRange.of(RatingClass.A, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public TumbleDryersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(TumbleDryersForm form, LegislationCategory legislationCategory, TumbleDryerSubCategory subCategory) {
    TemplatePopulator templatePopulator;
    if (subCategory == TumbleDryerSubCategory.AIR_VENTED_TUMBLE_DRYERS) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/tumble-dryers/air-vented-tumble-dryers.svg"));
    }
    else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/tumble-dryers/gas-fired-tumble-dryers.svg"));
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("kwhAnnum", form.getEnergyConsumption())
      .setText("minCycle", form.getCycleTime())
      .setText("kg", form.getRatedCapacity())
      .setText("db", form.getSoundPowerLevel())
      .getPopulatedDocument();
  }


  public Document generateHtml(CondenserTumbleDryersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/tumble-dryers/condenser-tumble-dryers.svg"));

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("kwhAnnum", form.getEnergyConsumption())
      .setText("minCycle", form.getCycleTime())
      .setText("kg", form.getRatedCapacity())
      .setText("db", form.getSoundPowerLevel())
      .applyRatingCssClass("condensationEfficiencyClass", RatingClass.valueOf(form.getCondensationEfficiencyRating()))
      .getPopulatedDocument();
  }

}
