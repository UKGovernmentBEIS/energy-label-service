package uk.co.fivium.els.categories.washingmachines.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.washingmachines.model.WashingMachinesForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.util.TemplateUtils;

@Service
public class WashingMachinesService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D),
      RatingClassRange.of(RatingClass.A, RatingClass.G), // spin class
      "labels/washing-machines/washing-machines.svg");

  private final TemplateParserService templateParserService;

  @Autowired
  public WashingMachinesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(WashingMachinesForm form, LegislationCategory legislationCategory) {

    Document templateDom = templateParserService.parseTemplate(legislationCategory.getTemplatePath());

    // TODO multiline
    TemplateUtils.setText(templateDom, "supplier", form.getSupplierName());
    TemplateUtils.setText(templateDom, "model", form.getModelName());
    TemplateUtils.setText(templateDom, "kwhAnnum", form.getAnnualEnergyConsumption());
    TemplateUtils.setText(templateDom, "lAnnum", form.getAnnualWaterConsumption());
    TemplateUtils.setText(templateDom, "kg", form.getCapacity());
    TemplateUtils.setText(templateDom, "washDb", form.getWashingNoiseEmissions());
    TemplateUtils.setText(templateDom, "spinDb", form.getSpinningNoiseEmissions());

    TemplateUtils.applyRatingCssClass(templateDom, "spinClass", RatingClass.valueOf(form.getSpinDryingEfficiencyRating()));

    TemplateUtils.setRatingArrow(templateDom, "rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange());

    return templateDom;
  }

}
