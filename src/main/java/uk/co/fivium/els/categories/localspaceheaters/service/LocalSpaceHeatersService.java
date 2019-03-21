package uk.co.fivium.els.categories.localspaceheaters.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.localspaceheaters.model.LocalSpaceHeatersForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class LocalSpaceHeatersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APP, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public LocalSpaceHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(LocalSpaceHeatersForm form) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/local-space-heaters/local-space-heaters.svg"));


    if (form.getFluidTransfer()) {
      templatePopulator.applyCssClassToId("indirectHeatSection", "hasIndirectHeatSection");
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("directHeatKw", form.getDirectHeatOutput())
      .setText("indirectHeatKw", form.getIndirectHeatOutput())
      .getPopulatedDocument();
  }
}
