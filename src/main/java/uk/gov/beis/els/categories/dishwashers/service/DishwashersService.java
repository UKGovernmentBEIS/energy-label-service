package uk.gov.beis.els.categories.dishwashers.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.dishwashers.model.DishwashersForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class DishwashersService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_PRE_MARCH_2021 = SelectableLegislationCategory.preMarch2021(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D),
      RatingClassRange.of(RatingClass.A, RatingClass.G)  // Drying class
  );

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_POST_MARCH_2021 = SelectableLegislationCategory.postMarch2021(
      RatingClassRange.of(RatingClass.A, RatingClass.D)  // Noise emission class
  );

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_PRE_MARCH_2021)
      .add(LEGISLATION_CATEGORY_POST_MARCH_2021)
      .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public DishwashersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(DishwashersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;

    if (legislationCategory.equals(LEGISLATION_CATEGORY_PRE_MARCH_2021)) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/dishwashers/dishwashers-2010.svg"));
      templatePopulator
          .setText("placeSettingsCapacity", form.getStandardCapacity())
          .setText("kwhAnnum", form.getAnnualEnergyConsumption())
          .setText("lAnnum", form.getAnnualWaterConsumption())
          .applyRatingCssClass("dryingClass", RatingClass.valueOf(form.getDryingEfficiencyRating()));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/dishwashers/dishwashers-2021.svg"));
      templatePopulator
          .setQrCode(form)
          .setText("placeSettingsCapacity", form.getEcoCapacity())
          .setText("kwh100cycles", form.getEnergyConsumptionPer100Cycles())
          .setText("lCycle", form.getWaterConsumptionPerCycle())
          .setHoursMinutes("duration", form.getProgrammeDurationHours(), form.getProgrammeDurationMinutes())
          .applyRatingCssClass("noiseClass", RatingClass.valueOf(form.getNoiseEmissionsClass()));
    }

    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("db", form.getNoiseEmissions())
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()),
            legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabel(ProductMetadata.DISHWASHERS, form);
  }

}
