package uk.gov.beis.els.categories.solidfuelboilers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.solidfuelboilers.model.SolidFuelBoilerPackagesForm;
import uk.gov.beis.els.categories.solidfuelboilers.model.SolidFuelBoilersForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class SolidFuelBoilersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  public static final LegislationCategory LEGISLATION_CATEGORY_PACKAGES_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G)
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public SolidFuelBoilersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(SolidFuelBoilersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/solid-fuel-boilers/solid-fuel-boilers.svg"));

    if (form.getCombination()) {
      templatePopulator.applyCssClassToId("combinationBoiler", "isCombinationBoiler");
    }

    if (form.getCogeneration()) {
      templatePopulator.applyCssClassToId("cogenerationBoiler", "isCogenerationBoiler");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kw", form.getRatedHeatOutput())
      .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .asProcessedEnergyLabel(ProductMetadata.SOLID_FUEL_BOILER, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(SolidFuelBoilerPackagesForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/solid-fuel-boilers/packages-of-a-solid-fuel-boiler-supplementary-heaters-temperature-controls-and-solar-devices.svg"));

    if (form.getSolarCollector()) {
      templatePopulator.applyCssClassToId("solarCollector", "hasSolarCollector");
    }
    if (form.getHotWaterStorageTank()) {
      templatePopulator.applyCssClassToId("hotWaterStorageTank", "hasHotWaterStorageTank");
    }
    if (form.getTemperatureControl()) {
      templatePopulator.applyCssClassToId("temperatureControl", "hasTemperatureControl");
    }
    if (form.getSpaceHeater()) {
      templatePopulator.applyCssClassToId("supplementarySpaceHeater", "hasSupplementarySpaceHeater");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("rating", RatingClass.getEnum(form.getPackageEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES_CURRENT.getPrimaryRatingRange())
      .setText("boilerRatingLetter", RatingClass.getEnum(form.getBoilerEfficiencyRating()).getLetter())
      .setText("boilerRatingPlusses", RatingClass.getEnum(form.getBoilerEfficiencyRating()).getPlusses())
      .asProcessedEnergyLabel(ProductMetadata.SOLID_FUEL_BOILER_PACKAGE, form);
  }
}
