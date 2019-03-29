package uk.gov.beis.els.categories.tumbledryers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.tumbledryers.model.CondenserTumbleDryersForm;
import uk.gov.beis.els.categories.tumbledryers.model.TumbleDryersForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

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

  public ProcessedEnergyLabelDocument generateHtmlGasFired(TumbleDryersForm form, LegislationCategory legislationCategory) {
    return generateHtml(form, legislationCategory, "labels/tumble-dryers/gas-fired-tumble-dryers.svg", ProductMetadata.TUMBLE_DRYERS_GAS_FIRED);
  }

  public ProcessedEnergyLabelDocument generateHtmlAirVented(TumbleDryersForm form, LegislationCategory legislationCategory) {
    return generateHtml(form, legislationCategory, "labels/tumble-dryers/air-vented-tumble-dryers.svg", ProductMetadata.TUMBLE_DRYERS_AIR_VENTED);
  }

  public ProcessedEnergyLabelDocument generateHtmlCondenser(CondenserTumbleDryersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/tumble-dryers/condenser-tumble-dryers.svg"));
    return applyCommonPopulation(templatePopulator, form, legislationCategory)
        .applyRatingCssClass("condensationEfficiencyClass", RatingClass.valueOf(form.getCondensationEfficiencyRating()))
        .asProcessedEnergyLabel(ProductMetadata.TUMBLE_DRYERS_CONDENSER, form);
  }

  private ProcessedEnergyLabelDocument generateHtml(TumbleDryersForm form, LegislationCategory legislationCategory, String templatePath, ProductMetadata productMetadata) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));
    return applyCommonPopulation(templatePopulator, form, legislationCategory)
        .asProcessedEnergyLabel(productMetadata, form);
  }

  private TemplatePopulator applyCommonPopulation(TemplatePopulator templatePopulator, TumbleDryersForm form, LegislationCategory legislationCategory) {
    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setText("kwhAnnum", form.getEnergyConsumption())
        .setText("minCycle", form.getCycleTime())
        .setText("kg", form.getRatedCapacity())
        .setText("db", form.getSoundPowerLevel());
  }

}
