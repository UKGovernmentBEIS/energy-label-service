package uk.co.fivium.els.categories.waterheaters.service;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.waterheaters.model.HeatPumpWaterHeatersForm;
import uk.co.fivium.els.categories.waterheaters.model.LoadProfile;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class WaterHeatersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.AP, RatingClass.F));

  private final TemplateParserService templateParserService;

  @Autowired
  public WaterHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(HeatPumpWaterHeatersForm form, LegislationCategory legislationCategory){
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/water-heaters/heat-pump-water-heaters.svg"));

    if (StringUtils.isBlank(form.getSoundPowerLevelIndoors())) {
      templatePopulator.removeElementById("insideDbSection");

    } else {
      templatePopulator
          .applyCssClassToId("insideDbSection", "hasInsideDb")
          .setText("insideDb", form.getSoundPowerLevelIndoors());
    }

    if (BooleanUtils.isTrue(form.getCanRunOffPeakOnly())) {
     templatePopulator.applyCssClassToId("offPeakMode","hasOffPeakMode");
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("declaredLoadProfile", LoadProfile.valueOf(form.getDeclaredLoadProfile()).getDisplayName())
      .setText("colderKwhAnnum", form.getColderKwhAnnum())
      .setText("averageKwhAnnum", form.getAverageKwhAnnum())
      .setText("warmerKwhAnnum", form.getWarmerKwhAnnum())
      .setText("colderGjAnnum", form.getColderGjAnnum())
      .setText("averageGjAnnum", form.getAverageGjAnnum())
      .setText("warmerGjAnnum", form.getWarmerGjAnnum())
      .setText("outsideDb", form.getSoundPowerLevelOutdoors())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .getPopulatedDocument();
  }
}
