package uk.co.fivium.els.categories.tumbledryers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.common.ProcessedEnergyLabelDocument;
import uk.co.fivium.els.categories.tumbledryers.model.CondenserTumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryersForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.ProductMetadata;
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
