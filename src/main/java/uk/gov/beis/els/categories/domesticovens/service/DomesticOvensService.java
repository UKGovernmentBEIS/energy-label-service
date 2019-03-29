package uk.gov.beis.els.categories.domesticovens.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.domesticovens.model.DomesticOvensForm;
import uk.gov.beis.els.categories.domesticovens.model.GasOvensForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class DomesticOvensService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.D));

  private final TemplateParserService templateParserService;

  @Autowired
  public DomesticOvensService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(DomesticOvensForm form) {
    return generateHtml("labels/domestic-ovens/electric-ovens.svg", form, null, null, ProductMetadata.OVENS_ELECTRIC);
  }

  public ProcessedEnergyLabelDocument generateHtml(GasOvensForm form) {
    return generateHtml("labels/domestic-ovens/gas-ovens.svg", form, form.getConvectionMjConsumption(), form.getConventionalMjConsumption(), ProductMetadata.OVENS_GAS);
  }

  private ProcessedEnergyLabelDocument generateHtml(String templatePath, DomesticOvensForm form, String convectionMjConsumption, String conventionalMjConsumption, ProductMetadata productMetadata) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));
      if (form.getIsFanOven()) {
        templatePopulator
          .applyCssClassToId("forcedAirSection", "hasForcedAirSection")
          .setText("forcedAirKwh", form.getConvectionKwhConsumption());

      if (StringUtils.isNotBlank(convectionMjConsumption)) {
        templatePopulator.setText("forcedAirMj", convectionMjConsumption);
      }
    }
    else {
      templatePopulator.removeElementById("forcedAirSection");
    }

    if (StringUtils.isNotBlank(conventionalMjConsumption)) {
      templatePopulator.setText("conventionalMj", conventionalMjConsumption);
    }

    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("conventionalKwh", form.getConventionalKwhConsumption())
        .setText("litres", form.getVolume())
        .asProcessedEnergyLabel(productMetadata, form);
  }

}
