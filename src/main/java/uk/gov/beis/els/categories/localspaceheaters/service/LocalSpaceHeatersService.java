package uk.gov.beis.els.categories.localspaceheaters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.localspaceheaters.model.LocalSpaceHeatersForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class LocalSpaceHeatersService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APP, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public LocalSpaceHeatersService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(LocalSpaceHeatersForm form) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/local-space-heaters/local-space-heaters.svg"));


    if (form.getFluidTransfer()) {
      templatePopulator.applyCssClassToId("indirectHeatSection", "hasIndirectHeatSection")
          .setText("indirectHeatKw", form.getIndirectHeatOutput());
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("directHeatKw", form.getDirectHeatOutput())
      .asProcessedEnergyLabel(ProductMetadata.LOCAL_SPACE_HEATERS, form);
  }
}
