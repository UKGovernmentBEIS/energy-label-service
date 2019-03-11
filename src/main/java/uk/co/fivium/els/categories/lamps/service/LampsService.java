package uk.co.fivium.els.categories.lamps.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.lamps.model.LampsForm;
import uk.co.fivium.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.co.fivium.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.co.fivium.els.categories.lamps.model.TemplateType;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.util.TemplateUtils;

@Service
public class LampsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APP, RatingClass.E),
      "labels/lamps/lamps.svg");

  private final TemplateParserService templateParserService;

  @Autowired
  public LampsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public void setMultilineText(Document document, String elementId, String textValue) {
    // get
  }

  public Document generateHtml(LampsForm form, LegislationCategory legislationCategory) {
    Document template = templateParserService.parseTemplate(legislationCategory.getTemplatePath());
    TemplateUtils.setMultilineText(template, "supplier", form.getSupplierName());
    TemplateUtils.setMultilineText(template, "model", form.getModelName());
    TemplateUtils.setRatingArrow(template, "rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange());
    TemplateUtils.setText(template, "kwh", form.getEnergyConsumption());

    return template;
  }

  public Document generateHtml(LampsFormNoSupplierModel form, LegislationCategory legislationCategory) {
    TemplateType templateType = TemplateType.valueOf(form.getTemplateType());
    Document template;
    if (templateType == TemplateType.COLOUR) {
      template = templateParserService.parseTemplate("labels/lamps/excluding-name-model/lamps-excluding-name-model.svg");
    } else {
      template = templateParserService.parseTemplate("labels/lamps/excluding-name-model/lamps-excluding-name-model-bw.svg");
    }

    TemplateUtils.setRatingArrow(template, "rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange());
    TemplateUtils.setText(template, "kwh", form.getEnergyConsumption());
    return template;
  }

  public Document generateHtml(LampsFormNoSupplierModelConsumption form, LegislationCategory legislationCategory) {
    TemplateType templateType = TemplateType.valueOf(form.getTemplateType());
    Document template;
    if (templateType == TemplateType.COLOUR) {
      template = templateParserService.parseTemplate("labels/lamps/excluding-name-model-consumption/lamps-excluding-name-model-consumption.svg");
    } else {
      template = templateParserService.parseTemplate("labels/lamps/excluding-name-model-consumption/lamps-excluding-name-model-consumption-bw.svg");
    }
    TemplateUtils.setRatingArrow(template, "rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange());
    return template;
  }

}
