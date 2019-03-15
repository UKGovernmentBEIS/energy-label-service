package uk.co.fivium.els.categories.televisions.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.televisions.model.TelevisionSubCategory;
import uk.co.fivium.els.categories.televisions.model.TelevisionsForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class TelevisionsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_JAN2017 = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APP, RatingClass.E));
  public static final LegislationCategory LEGISLATION_CATEGORY_JAN2020 = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  private final TemplateParserService templateParserService;

  @Autowired
  public TelevisionsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(TelevisionsForm form, TelevisionSubCategory televisionSubCategory) {
    TemplatePopulator templatePopulator;
    LegislationCategory legislationCategory;
    if (televisionSubCategory == TelevisionSubCategory.TELEVISIONS_FROM_JAN2017) {
      legislationCategory = TelevisionsService.LEGISLATION_CATEGORY_JAN2017;
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/televisions/televisions-2017.svg"));
    } else {
      legislationCategory = TelevisionsService.LEGISLATION_CATEGORY_JAN2020;
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/televisions/televisions-2020.svg"));
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("watt", form.getPowerConsumption())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("cm", form.getScreenSizeCm())
      .setText("inch", form.getScreenSizeInch())
      .applyCssClassToId("powerSwitch", "hasPowerSwitch")
      .getPopulatedDocument();
  }
}
