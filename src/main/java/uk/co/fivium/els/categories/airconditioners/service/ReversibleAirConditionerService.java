package uk.co.fivium.els.categories.airconditioners.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.airconditioners.model.ReversibleAirConditionersForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class ReversibleAirConditionerService {

  public static final LegislationCategory LEGISLATION_CATEGORY_JAN2019 = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  private final TemplateParserService templateParserService;

  @Autowired
  public ReversibleAirConditionerService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(ReversibleAirConditionersForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/air-conditioners/non-duct/reversible-air-conditioners.svg"));
    return templatePopulator
        .setText("supplier", form.getSupplierName())
        .setText("model", form.getModelIdentifier())
        .setRatingArrow("seerRating", RatingClass.valueOf(form.getOverallEfficiencyClass()), legislationCategory.getPrimaryRatingRange())
        // TODO all the rest
        .getPopulatedDocument();
  }




}
