package uk.co.fivium.els.categories.spaceheaters.service;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.domesticovens.model.DomesticOvensForm;
import uk.co.fivium.els.categories.domesticovens.model.GasOvensForm;
import uk.co.fivium.els.categories.spaceheaters.model.HeatPumpSpaceHeatersForm;
import uk.co.fivium.els.categories.spaceheaters.model.LowTemperatureHeatPumpSpaceHeatersForm;
import uk.co.fivium.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.model.SelectableLegislationCategory;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

import java.util.List;

@Service
public class SpaceHeatersService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JAN2015 = SelectableLegislationCategory.of(
    "JAN2015",
    "From 1 January 2015",
    RatingClassRange.of(RatingClass.APP, RatingClass.G),
    RatingClassRange.of(RatingClass.A, RatingClass.G));

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_JAN2019 = SelectableLegislationCategory.of(
    "JAN2019",
    "From 1 January 2019",
    RatingClassRange.of(RatingClass.APPP, RatingClass.D),
    RatingClassRange.of(RatingClass.AP, RatingClass.F));

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
    .add(LEGISLATION_CATEGORY_JAN2015)
    .add(LEGISLATION_CATEGORY_JAN2019)
    .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public SpaceHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(LowTemperatureHeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_JAN2015) {
      templatePath = "labels/space-heaters/low-temperature-heat-pump-space-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/low-temperature-heat-pump-space-heaters-2019.svg";
    }
      return generateHtml(templatePath, form, legislationCategory, null, null, null, null);
  }

  public Document generateHtml(HeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory) {
    String templatePath;
    if (legislationCategory == LEGISLATION_CATEGORY_JAN2015) {
      templatePath = "labels/space-heaters/heat-pump-space-heaters-2015.svg";
    }
    else {
      templatePath = "labels/space-heaters/heat-pump-space-heaters-2019.svg";
    }
    return generateHtml(templatePath, form, legislationCategory, form.getMediumTempEfficiencyRating(), form.getMediumTempColderHeatOutput(), form.getMediumTempAverageHeatOutput(), form.getMediumTempWarmerHeatOutput());
  }

  private Document generateHtml(String templatePath, LowTemperatureHeatPumpSpaceHeatersForm form, LegislationCategory legislationCategory, String mediumTempEfficiencyRating, String mediumTempColderHeatOutput, String mediumTempAverageHeatOutput, String mediumTempWarmerHeatOutput) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));
    if (StringUtils.isNotBlank(form.getSoundPowerLevelIndoors())) {
      templatePopulator
        .applyCssClassToId("insideDbSection", "hasInsideDb")
        .setText("insideDb", form.getSoundPowerLevelIndoors());
    }
    else {
      templatePopulator.removeElementById("insideDbSection");
    }

    if (StringUtils.isNotBlank(mediumTempEfficiencyRating)) {
      templatePopulator.setRatingArrow("mediumTemperatureRating", RatingClass.valueOf(mediumTempEfficiencyRating), legislationCategory.getPrimaryRatingRange());
    }
    if (StringUtils.isNotBlank(mediumTempColderHeatOutput)) {
      templatePopulator.setText("mediumTemperatureColderKw", mediumTempColderHeatOutput);
    }
    if (StringUtils.isNotBlank(mediumTempAverageHeatOutput)) {
      templatePopulator.setText("mediumTemperatureAverageKw", mediumTempAverageHeatOutput);
    }
    if (StringUtils.isNotBlank(mediumTempWarmerHeatOutput)) {
      templatePopulator.setText("mediumTemperatureWarmerKw", mediumTempWarmerHeatOutput);
    }


    return templatePopulator
      .setRatingArrow("lowTemperatureRating", RatingClass.valueOf(form.getLowTempEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("lowTemperatureColderKw", form.getLowTempColderHeatOutput())
      .setText("lowTemperatureAverageKw", form.getLowTempAverageHeatOutput())
      .setText("lowTemperatureWarmerKw", form.getLowTempWarmerHeatOutput())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .getPopulatedDocument();
  }

}
