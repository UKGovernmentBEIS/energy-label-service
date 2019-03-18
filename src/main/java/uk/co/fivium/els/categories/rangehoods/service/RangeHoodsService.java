package uk.co.fivium.els.categories.rangehoods.service;

import com.google.common.collect.ImmutableList;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.rangehoods.model.RangeHoodsForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

import java.util.List;

@Service
public class RangeHoodsService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JAN2018 = SelectableLegislationCategory.of(
      "JAN2018",
      "Between 1 January 2018 and 31 December 2019",
      RatingClassRange.of(RatingClass.APP, RatingClass.E));
  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JAN2020 = SelectableLegislationCategory.of(
      "JAN2020",
      "From 1 January 2020",
      RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_JAN2018)
      .add(LEGISLATION_CATEGORY_JAN2020)
      .build();

  public static final RatingClassRange SECONDARY_CLASS_RANGE = RatingClassRange.of(RatingClass.A, RatingClass.G);

  private final TemplateParserService templateParserService;

  @Autowired
  public RangeHoodsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(RangeHoodsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;
    if (legislationCategory == RangeHoodsService.LEGISLATION_CATEGORY_JAN2018) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/range-hoods/range-hoods-2018.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/range-hoods/range-hoods-2020.svg"));
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("db", form.getNoiseValue())
      .applyRatingCssClass("fluidDynamicClass", RatingClass.valueOf(form.getFluidClass()))
      .applyRatingCssClass("lightingClass", RatingClass.valueOf(form.getLightingClass()))
      .applyRatingCssClass("greaseFilteringClass", RatingClass.valueOf(form.getGreaseClass()))
      .getPopulatedDocument();
  }
}
