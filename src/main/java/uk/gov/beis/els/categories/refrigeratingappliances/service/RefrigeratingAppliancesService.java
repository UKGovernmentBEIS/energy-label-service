package uk.gov.beis.els.categories.refrigeratingappliances.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FridgesFreezersForm;
import uk.gov.beis.els.categories.refrigeratingappliances.model.WineStorageAppliancesForm;
import uk.gov.beis.els.model.InternetLabelTemplate;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class RefrigeratingAppliancesService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.A, RatingClass.G),
      RatingClassRange.of(RatingClass.A, RatingClass.D),  // Noise emission class
      InternetLabelTemplate.RESCALED
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public RefrigeratingAppliancesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(FridgesFreezersForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
        "labels/household-refrigerating-appliances/household-refrigerating-appliances-2021.svg"));

    if (form.getFrozenCompartment()) {
      templatePopulator
          .setText("freezerLitres", form.getFrozenVolume())
          .applyCssClassToId("freezerSection", "hasFreezerSection");

      if (!form.getChillCompartment()) {
        templatePopulator.setElementTranslate("freezerSection", 65.3, 0);
      }
    }

    if (form.getChillCompartment()) {
      templatePopulator
          .setText("fridgeLitres", form.getChillVolume())
          .applyCssClassToId("fridgeSection", "hasFridgeSection");

      if (!form.getFrozenCompartment()) {
        templatePopulator.setElementTranslate("fridgeSection", -63.5, 0);
      }
    }

    return templatePopulator
        .setQrCode(form.getQrCodeUrl())
        .applyRatingCssClass("noiseClass", RatingClass.getEnum(form.getNoiseEmissionsClass()))
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()),
            LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .setText("db", form.getNoiseEmissions())
        .asProcessedEnergyLabel(ProductMetadata.HRA_FRIDGE_FREEZER, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(WineStorageAppliancesForm form) {
    return new TemplatePopulator(templateParserService.parseTemplate(
        "labels/household-refrigerating-appliances/wine-storage-appliances-2021.svg"))
        .setQrCode(form.getQrCodeUrl())
        .applyRatingCssClass("noiseClass", RatingClass.getEnum(form.getNoiseEmissionsClass()))
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()),
            LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .setText("bottleCapacity", form.getBottleCapacity())
        .setText("db", form.getNoiseEmissions())
        .asProcessedEnergyLabel(ProductMetadata.HRA_WINE_STORAGE, form);
  }
}
