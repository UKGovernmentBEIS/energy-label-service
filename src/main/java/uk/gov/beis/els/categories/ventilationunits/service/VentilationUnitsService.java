package uk.gov.beis.els.categories.ventilationunits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.ventilationunits.model.VentilationUnitsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class VentilationUnitsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.AP, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public VentilationUnitsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtmlUnidirectional(VentilationUnitsForm form, LegislationCategory legislationCategory) {
    return generateHtml(form, legislationCategory, "labels/ventilation-units/unidirectional-ventilation-unit.svg", ProductMetadata.VENTILATION_UNITS_UNIDIRECTIONAL);
  }

  public ProcessedEnergyLabelDocument generateHtmlBidirectional(VentilationUnitsForm form, LegislationCategory legislationCategory) {
    return generateHtml(form, legislationCategory, "labels/ventilation-units/bidirectional-ventilation-unit.svg", ProductMetadata.VENTILATION_UNITS_BIDIRECTIONAL);
  }

  private ProcessedEnergyLabelDocument generateHtml(VentilationUnitsForm form, LegislationCategory legislationCategory, String templatePath, ProductMetadata productMetadata) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));
    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("db", form.getSoundPowerLevel())
        .setText("m3h", form.getMaxFlowRate())
        .asProcessedEnergyLabel(productMetadata, form);
  }
}
