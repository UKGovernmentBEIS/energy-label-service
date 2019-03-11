package uk.co.fivium.elp.categories.airconditioners.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.elp.categories.airconditioners.model.ReversibleAirConditionersForm;
import uk.co.fivium.elp.model.LegislationCategory;
import uk.co.fivium.elp.model.RatingClass;
import uk.co.fivium.elp.model.RatingClassRange;
import uk.co.fivium.elp.service.TemplateParserService;
import uk.co.fivium.elp.util.TemplateUtils;

@Service
public class ReversibleAirConditionerService {

  public static final LegislationCategory LEGISLATION_CATEGORY_JAN2019 = LegislationCategory.of(RatingClassRange.of(RatingClass.APPP, RatingClass.D),
      "labels/air-conditioners/non-duct/reversible-air-conditioners-2019.svg");

  private final TemplateParserService templateParserService;

  @Autowired
  public ReversibleAirConditionerService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(ReversibleAirConditionersForm form, LegislationCategory legislationCategory) {

    Document templateDom = templateParserService.parseTemplate(legislationCategory.getTemplatePath());

    TemplateUtils.setText(templateDom, "supplier", form.getSupplierName());
    TemplateUtils.setText(templateDom, "model", form.getModelIdentifier());
    TemplateUtils.setRatingArrow(templateDom, "seerRating", RatingClass.valueOf(form.getOverallEfficiencyClass()), legislationCategory.getPrimaryRatingRange());

    // TODO all the rest

    return templateDom;
  }




}
