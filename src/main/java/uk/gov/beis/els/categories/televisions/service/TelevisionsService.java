package uk.gov.beis.els.categories.televisions.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class TelevisionsService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JAN2017 = SelectableLegislationCategory.of(
      "JAN2017",
      "From 1 January 2017",
      RatingClassRange.of(RatingClass.APP, RatingClass.E));
  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JAN2020 = SelectableLegislationCategory.of(
      "JAN2020",
      "From 1 January 2020",
      RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_JAN2017)
      .add(LEGISLATION_CATEGORY_JAN2020)
      .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public TelevisionsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(TelevisionsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;
    if (legislationCategory == TelevisionsService.LEGISLATION_CATEGORY_JAN2017) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/televisions/televisions-2017.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/televisions/televisions-2020.svg"));
    }

    if (form.getPowerSwitch()) {
      templatePopulator.applyCssClassToId("powerSwitch", "hasPowerSwitch");
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("watt", form.getPowerConsumption())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("cm", form.getScreenSizeCm())
      .setText("inch", form.getScreenSizeInch())
      .asProcessedEnergyLabel(ProductMetadata.TV, form);
  }
}
