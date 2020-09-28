package uk.gov.beis.els.categories.televisions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class TelevisionsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  private final TemplateParserService templateParserService;

  @Autowired
  public TelevisionsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(TelevisionsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/televisions/televisions.svg"));

    if (form.getPowerSwitch()) {
      templatePopulator.applyCssClassToId("powerSwitch", "hasPowerSwitch");
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("watt", form.getPowerConsumption())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("cm", form.getScreenSizeCm())
      .setText("inch", form.getScreenSizeInch())
      .asProcessedEnergyLabel(ProductMetadata.TV, form);
  }
}
