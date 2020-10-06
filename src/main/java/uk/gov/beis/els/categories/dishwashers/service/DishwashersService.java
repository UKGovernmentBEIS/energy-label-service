package uk.gov.beis.els.categories.dishwashers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.dishwashers.model.DishwashersForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class DishwashersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D),
      RatingClassRange.of(RatingClass.A, RatingClass.G)); // drying class

  private final TemplateParserService templateParserService;

  @Autowired
  public DishwashersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(DishwashersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/dishwashers/dishwashers-2010.svg"));

    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .setText("lAnnum", form.getAnnualWaterConsumption())
        .setText("placeSettingsCapacity", form.getCapacity())
        .setText("db", form.getNoiseEmissions())
        .applyRatingCssClass("dryingClass", RatingClass.valueOf(form.getDryingEfficiencyRating()))
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabel(ProductMetadata.DISHWASHERS, form);
  }

}
