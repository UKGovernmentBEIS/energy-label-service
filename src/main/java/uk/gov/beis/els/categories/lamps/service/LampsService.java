package uk.gov.beis.els.categories.lamps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.lamps.model.LampsForm;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.gov.beis.els.categories.lamps.model.TemplateType;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class LampsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APP, RatingClass.E));

  private final TemplateParserService templateParserService;

  @Autowired
  public LampsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps/lamps.svg"));

    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setText("kwh", form.getEnergyConsumption())
        .asProcessedEnergyLabel(ProductMetadata.LAMPS_FULL, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsFormNoSupplierModel form, LegislationCategory legislationCategory) {
    TemplateType templateType = TemplateType.valueOf(form.getTemplateType());
    TemplatePopulator templatePopulator;

    if (templateType == TemplateType.COLOUR) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps/excluding-name-model/lamps-excluding-name-model.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps/excluding-name-model/lamps-excluding-name-model-bw.svg"));
    }

    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setText("kwh", form.getEnergyConsumption())
        .asProcessedEnergyLabelNoSupplier(ProductMetadata.LAMPS_RATING_CONSUMPTION, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsFormNoSupplierModelConsumption form, LegislationCategory legislationCategory) {
    TemplateType templateType = TemplateType.valueOf(form.getTemplateType());
    TemplatePopulator templatePopulator;

    if (templateType == TemplateType.COLOUR) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps/excluding-name-model-consumption/lamps-excluding-name-model-consumption.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps/excluding-name-model-consumption/lamps-excluding-name-model-consumption-bw.svg"));
    }

    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabelNoSupplier(ProductMetadata.LAMPS_RATING, form);
  }

}
