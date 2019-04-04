package uk.gov.beis.els.categories.prorefrigeratedcabinets.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ClimateClass;
import uk.gov.beis.els.categories.prorefrigeratedcabinets.model.ProRefrigeratedCabinetsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class ProRefrigeratedCabinetsService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JUL2016 = SelectableLegislationCategory.of(
      "JUN2018",
      "From 1 July 2016",
      RatingClassRange.of(RatingClass.A, RatingClass.G));
  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JUL2019 = SelectableLegislationCategory.of(
      "JUN2020",
      "From 1 July 2019",
      RatingClassRange.of(RatingClass.APPP, RatingClass.G));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_JUL2016)
      .add(LEGISLATION_CATEGORY_JUL2019)
      .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public ProRefrigeratedCabinetsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(ProRefrigeratedCabinetsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;
    if (legislationCategory.equals(LEGISLATION_CATEGORY_JUL2016)) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/professional-refrigerated-storage-cabinets/professional-refrigerated-storage-cabinets-2016.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/professional-refrigerated-storage-cabinets/professional-refrigerated-storage-cabinets-2019.svg"));
    }

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
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .applyCssClassToId("climateClass", ClimateClass.valueOf(form.getClimateClass()).getSvgClass())
      .asProcessedEnergyLabel(ProductMetadata.PRO_REFRIGERATED_CABINETS, form);
  }
}
