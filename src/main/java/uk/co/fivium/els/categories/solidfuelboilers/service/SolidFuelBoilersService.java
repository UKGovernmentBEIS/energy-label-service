package uk.co.fivium.els.categories.solidfuelboilers.service;

import com.google.common.collect.ImmutableList;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilerPackagesForm;
import uk.co.fivium.els.categories.solidfuelboilers.model.SolidFuelBoilersForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

import java.util.List;

@Service
public class SolidFuelBoilersService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_BOILERS_APR2017 = SelectableLegislationCategory.of(
    "APR2017",
    "From 1 April 2017",
    RatingClassRange.of(RatingClass.APP, RatingClass.G));

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_BOILERS_SEP2019 = SelectableLegislationCategory.of(
    "SEP2019",
    "From 26 September 2019",
    RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES_BOILERS = new ImmutableList.Builder<SelectableLegislationCategory>()
    .add(LEGISLATION_CATEGORY_BOILERS_APR2017)
    .add(LEGISLATION_CATEGORY_BOILERS_SEP2019)
    .build();

  public static final LegislationCategory LEGISLATION_CATEGORY_PACKAGES_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G)
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public SolidFuelBoilersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(SolidFuelBoilersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator;
    if (legislationCategory == LEGISLATION_CATEGORY_BOILERS_APR2017) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/solid-fuel-boilers/solid-fuel-boilers-2017.svg"));
    }
    else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/solid-fuel-boilers/solid-fuel-boilers-2019.svg"));
    }

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
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }

  public Document generateHtml(SolidFuelBoilerPackagesForm form) {
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
      .setRatingArrow("rating", RatingClass.valueOf(form.getPackageEfficiencyRating()), LEGISLATION_CATEGORY_PACKAGES_CURRENT.getPrimaryRatingRange())
      .setText("boilerRatingLetter", RatingClass.valueOf(form.getBoilerEfficiencyRating()).getLetter())
      .setText("boilerRatingPlusses", RatingClass.valueOf(form.getBoilerEfficiencyRating()).getPlusses())
      .getPopulatedDocument();
  }
}
