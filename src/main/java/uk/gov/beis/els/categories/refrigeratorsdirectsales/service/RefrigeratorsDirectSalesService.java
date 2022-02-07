package uk.gov.beis.els.categories.refrigeratorsdirectsales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.BeverageCoolersForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.DisplayCabinetsForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.IceCreamFreezersForm;
import uk.gov.beis.els.categories.refrigeratorsdirectsales.model.VendingMachinesForm;
import uk.gov.beis.els.model.InternetLabelTemplate;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class RefrigeratorsDirectSalesService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.A, RatingClass.G),
    InternetLabelTemplate.RESCALED
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public RefrigeratorsDirectSalesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(IceCreamFreezersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/refrigerators-direct-sales/ice-cream-freezers.svg"));

    return templatePopulator
        .setQrCode(form.getQrCodeUrl())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .setText("capacity", form.getCapacity())
        .setText("compartmentTemp", form.getCompartmentTemp())
        .setText("maxAmbientTemp", form.getMaxAmbientTemp())
        .asProcessedEnergyLabel(ProductMetadata.ICE_CREAM_FREEZERS, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(BeverageCoolersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/refrigerators-direct-sales/commercial-refrigeration-beverage.svg"));

    return templatePopulator
        .setQrCode(form.getQrCodeUrl())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .setText("capacity", form.getCapacity())
        .setText("compartmentTemp", form.getCompartmentTemp())
        .setText("maxAmbientTemp", form.getMaxAmbientTemp())
        .asProcessedEnergyLabel(ProductMetadata.BEVERAGE_COOLERS, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(VendingMachinesForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/refrigerators-direct-sales/refrigerators-direct-sales.svg"));

    if (form.getFrozenCompartment()) {
      templatePopulator.applyCssClassToId("freezerSection", "hasFreezerSection");
      templatePopulator.setText("freezerMaxTemp", form.getFreezerMaxTemp());
    } else {
      templatePopulator.setElementTranslate("fridgeSection", 0, 35);
    }

    return templatePopulator
        .setQrCode(form.getQrCodeUrl())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .applyCssClassToId("fridgeSection", "hasFridgeSection")
        .setText("fridgeCapacity", form.getFridgeCapacity())
        .applyCssClassToId("fridgeCapacityUnits", "fridgeCapacityUnitsL")
        .setText("fridgeMaxTemp", form.getFridgeMaxTemp())
        .removeElementById("fridgeMinTempSection")
        .removeElementById("freezerMinTempSection")
        .asProcessedEnergyLabel(ProductMetadata.VENDING_MACHINES, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(DisplayCabinetsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/refrigerators-direct-sales/refrigerators-direct-sales.svg"));

    if (form.getChilledCompartment()) {
      templatePopulator.applyCssClassToId("fridgeSection", "hasFridgeSection")
          .setText("fridgeCapacity", form.getFridgeCapacity())
          .applyCssClassToId("fridgeCapacityUnits", "fridgeCapacityUnitsm2")
          .setText("fridgeMaxTemp", form.getFridgeMaxTemp())
          .setText("fridgeMinTemp", form.getFridgeMinTemp());

      if(!form.getFrozenCompartment()) {
        templatePopulator.setElementTranslate("fridgeSection", 0, 35);
      }
    }

    if (form.getFrozenCompartment()) {
      templatePopulator.applyCssClassToId("freezerSection", "hasFreezerSection");
      templatePopulator.applyCssClassToId("freezerCapacitySection", "hasFreezerCapacitySection")
          .setText("freezerCapacity", form.getFreezerCapacity())
          .setText("freezerMaxTemp", form.getFreezerMaxTemp())
          .setText("freezerMinTemp", form.getFreezerMinTemp());

      if(!form.getChilledCompartment()) {
        templatePopulator.setElementTranslate("freezerSection", 0, -44.5);
      }
    }

    return templatePopulator
        .setQrCode(form.getQrCodeUrl())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("kwhAnnum", form.getAnnualEnergyConsumption())
        .asProcessedEnergyLabel(ProductMetadata.DISPLAY_CABINETS, form);
  }
}
