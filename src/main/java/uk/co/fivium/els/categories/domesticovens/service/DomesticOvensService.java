package uk.co.fivium.els.categories.domesticovens.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.domesticovens.model.DomesticOvensForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class DomesticOvensService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  private final TemplateParserService templateParserService;

  @Autowired
  public DomesticOvensService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  // Electric Ovens
  public Document generateHtml(DomesticOvensForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/domestic-ovens/electric-ovens.svg"));

    if (form.getIsFanOven()) {
      templatePopulator
        .applyCssClassToId("forcedAirSection", "hasForcedAirSection")
        .setText("forcedAirKwh", form.getConvectionKwhConsumption());
    }
    else {
      templatePopulator.removeElementById("forcedAirSection");
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("conventionalKwh", form.getConventionalKwhConsumption())
      .setText("litres", form.getVolume())
      .getPopulatedDocument();
  }
}
