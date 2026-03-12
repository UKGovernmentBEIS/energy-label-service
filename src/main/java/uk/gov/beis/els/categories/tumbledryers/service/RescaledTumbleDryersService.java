package uk.gov.beis.els.categories.tumbledryers.service;

import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.tumbledryers.model.rescaled.RescaledTumbleDryersForm;
import uk.gov.beis.els.model.InternetLabelTemplate;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class RescaledTumbleDryersService {
  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.A, RatingClass.G),
      RatingClassRange.of(RatingClass.A, RatingClass.D),
      RatingClassRange.of(RatingClass.A, RatingClass.E),
      InternetLabelTemplate.RESCALED);
  
  private final TemplateParserService templateParserService;

  public RescaledTumbleDryersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(RescaledTumbleDryersForm form) {
    var templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/tumble-dryers/tumble-dryers-2025.svg"));
    
    if (form.getHasRepairabilityRating() && form.getIsCondensing()) {
        templatePopulator
            .applyRatingCssClassToId("subscale", "condensingClass", RatingClass.getEnum(form.getCondensationEfficiencyClass()))
            .applyRatingCssClassToId("subscale", "repairabilityClass", RatingClass.getEnum(form.getRepairabilityRating()));
    } else if (!form.getHasRepairabilityRating() && form.getIsCondensing()) {
      templatePopulator
          .applyRatingCssClassToId("subscale", "condensingClass", RatingClass.getEnum(form.getCondensationEfficiencyClass()))
          .removeElementById("repairabilityIcon")
          .setElementTranslate("condensingIcon", -42, 0)
          .setElementTranslate("soundIcon", -24, 0);
    } else if (form.getHasRepairabilityRating() && !form.getIsCondensing()) {
      templatePopulator
          .applyRatingCssClassToId("subscale", "repairabilityClass", RatingClass.getEnum(form.getRepairabilityRating()))
          .removeElementById("condensingIcon")
          .setElementTranslate("repairabilityIcon", 43, 0)
          .setElementTranslate("soundIcon", -24, 0);
    }  else {
      templatePopulator
          .removeElementById("repairabilityIcon")
          .removeElementById("condensingIcon")
          .setElementTranslate("soundIcon", -75.5, 0);
    }
    
    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setQrCode(form.getQrCodeUrl())
        .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
        .applyRatingCssClassToId("subscale", "soundClass", RatingClass.getEnum(form.getNoiseEmissionsClass()))
        .setText("kg", form.getEcoCapacity())
        .setText("kwh100cycles", form.getEnergyConsumptionPer100Cycles())
        .setHoursMinutes("duration", form.getProgrammeDurationHours(), form.getProgrammeDurationMinutes())
        .setText("db", form.getNoiseEmissions())
        .asProcessedEnergyLabel(ProductMetadata.TUMBLE_DRYERS_RESCALED, form);
  }
}
