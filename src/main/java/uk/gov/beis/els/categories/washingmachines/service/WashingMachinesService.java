package uk.gov.beis.els.categories.washingmachines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class WashingMachinesService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D),
      RatingClassRange.of(RatingClass.A, RatingClass.G)); // spin class

  private final TemplateParserService templateParserService;

  @Autowired
  public WashingMachinesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(WashingMachinesForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/washing-machines/washing-machines.svg"));


    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .setText("lAnnum", form.getAnnualWaterConsumption())
        .setText("kg", form.getCapacity())
        .setText("washDb", form.getWashingNoiseEmissions())
        .setText("spinDb", form.getSpinningNoiseEmissions())
        .applyRatingCssClass("spinClass", RatingClass.valueOf(form.getSpinDryingEfficiencyRating()))
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabel(ProductMetadata.WASHING_MACHINES, form);
  }

}
