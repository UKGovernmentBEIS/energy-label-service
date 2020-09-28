package uk.gov.beis.els.categories.rangehoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.rangehoods.model.RangeHoodsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class RangeHoodsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  public static final RatingClassRange SECONDARY_CLASS_RANGE = RatingClassRange.of(RatingClass.A, RatingClass.G);

  private final TemplateParserService templateParserService;

  @Autowired
  public RangeHoodsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(RangeHoodsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/range-hoods/range-hoods.svg"));

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("db", form.getNoiseValue())
      .applyRatingCssClass("fluidDynamicClass", RatingClass.valueOf(form.getFluidClass()))
      .applyRatingCssClass("lightingClass", RatingClass.valueOf(form.getLightingClass()))
      .applyRatingCssClass("greaseFilteringClass", RatingClass.valueOf(form.getGreaseClass()))
      .asProcessedEnergyLabel(ProductMetadata.RANGE_HOODS, form);
  }
}
