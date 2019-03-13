package uk.co.fivium.els.categories.ventilationunits.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.ventilationunits.model.VentilationUnitSubCategory;
import uk.co.fivium.els.categories.ventilationunits.model.VentilationUnitsForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class VentilationUnitsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.AP, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public VentilationUnitsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(VentilationUnitsForm form, LegislationCategory legislationCategory, VentilationUnitSubCategory ventilationUnitSubCategory) {
    TemplatePopulator templatePopulator;
    if (ventilationUnitSubCategory == VentilationUnitSubCategory.UNIDIRECTIONAL_VENTILATION_UNITS) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/ventilation-units/unidirectional-ventilation-unit.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/ventilation-units/bidirectional-ventilation-unit.svg"));
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("db", form.getSoundPowerLevel())
      .setText("m3h", form.getMaxFlowRate())
      .getPopulatedDocument();
  }
}
