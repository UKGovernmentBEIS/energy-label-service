package uk.gov.beis.els.categories.prorefrigeratedcabinets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ClimateClass;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ProRefrigeratedCabinetsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class ProRefrigeratedCabinetsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public ProRefrigeratedCabinetsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(ProRefrigeratedCabinetsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/professional-refrigerated-storage-cabinets/professional-refrigerated-storage-cabinets.svg"));

    if (form.getChilledCompartment()) {
      templatePopulator
        .setText("fridgeLitres", form.getChilledVolume());
    }
    else {
      templatePopulator
        .setText("fridgeLitres", "-");
    }

    if (form.getFrozenCompartment()) {
      templatePopulator
        .setText("freezerLitres", form.getFrozenVolume());
    }
    else {
      templatePopulator
        .setText("freezerLitres", "-");
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .applyCssClassToId("climateClass", ClimateClass.valueOf(form.getClimateClass()).getSvgClass())
      .asProcessedEnergyLabel(ProductMetadata.PRO_REFRIGERATED_CABINETS, form);
  }
}
