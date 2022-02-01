package uk.gov.beis.els.categories.dishwashers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.dishwashers.model.DishwashersForm;
import uk.gov.beis.els.model.InternetLabelTemplate;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class DishwashersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.A, RatingClass.G),
      RatingClassRange.of(RatingClass.A, RatingClass.D),  // Noise emission class
      InternetLabelTemplate.RESCALED
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public DishwashersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(DishwashersForm form) {
    return new TemplatePopulator(templateParserService.parseTemplate("labels/dishwashers/dishwashers-2021.svg"))
        .setQrCode(form.getQrCodeUrl())
        .setText("placeSettingsCapacity", form.getEcoCapacity())
        .setText("kwh100cycles", form.getEnergyConsumptionPer100Cycles())
        .setText("lCycle", form.getWaterConsumptionPerCycle())
        .setHoursMinutes("duration", form.getProgrammeDurationHours(), form.getProgrammeDurationMinutes())
        .applyRatingCssClass("noiseClass", RatingClass.valueOf(form.getNoiseEmissionsClass()))
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("db", form.getNoiseEmissions())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()),
            LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
        .asProcessedEnergyLabel(ProductMetadata.DISHWASHERS, form);
  }

}
