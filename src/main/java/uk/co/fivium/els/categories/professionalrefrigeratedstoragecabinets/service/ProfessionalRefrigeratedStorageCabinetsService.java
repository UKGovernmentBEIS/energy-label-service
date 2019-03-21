package uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.service;

import com.google.common.collect.ImmutableList;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.model.ClimateClass;
import uk.co.fivium.els.categories.professionalrefrigeratedstoragecabinets.model.ProfessionalRefrigeratedStorageCabinetsForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

import java.util.List;

@Service
public class ProfessionalRefrigeratedStorageCabinetsService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JUL2016 = SelectableLegislationCategory.of(
      "JAN2018",
      "From 1 July 2016",
      RatingClassRange.of(RatingClass.A, RatingClass.G));
  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JUL2019 = SelectableLegislationCategory.of(
      "JAN2020",
      "From 1 July 2019",
      RatingClassRange.of(RatingClass.APPP, RatingClass.G));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_JUL2016)
      .add(LEGISLATION_CATEGORY_JUL2019)
      .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public ProfessionalRefrigeratedStorageCabinetsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(ProfessionalRefrigeratedStorageCabinetsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;
    if (legislationCategory == ProfessionalRefrigeratedStorageCabinetsService.LEGISLATION_CATEGORY_JUL2016) {
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
      .getPopulatedDocument();
  }
}
