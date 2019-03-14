package uk.co.fivium.els.categories.waterheaters.service;

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

    if (form.getSoundPowerLevelIndoors() != "") {
      templatePopulator = templatePopulator
        .applyCssClassToId("insideDbSection", "hasInsideDb")
        .setText("insideDb", form.getSoundPowerLevelIndoors());
    }
    // TODO Investigate why the text inside the invisible element isn't being hidden in the PDF, and if fixed, remove the below
    else {
      templatePopulator = templatePopulator.removeElementById("insideDbSection");
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
