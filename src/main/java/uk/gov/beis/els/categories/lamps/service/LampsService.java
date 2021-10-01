package uk.gov.beis.els.categories.lamps.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.lamps.model.LampsForm;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.gov.beis.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.gov.beis.els.categories.lamps.model.LampsFormPackagingArrow;
import uk.gov.beis.els.categories.lamps.model.LightSourceArrowOrientation;
import uk.gov.beis.els.categories.lamps.model.TemplateColour;
import uk.gov.beis.els.categories.lamps.model.TemplateSize;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class LampsService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021 = SelectableLegislationCategory.preSeptember2021(
          RatingClassRange.of(RatingClass.APP, RatingClass.E)
  );

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_POST_SEPTEMBER_2021 = SelectableLegislationCategory.postSeptember2021();

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
          .add(LEGISLATION_CATEGORY_POST_SEPTEMBER_2021)
          .add(LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021)
          .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public LampsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator;

    if(legislationCategory.equals(LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021)) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps-light-sources/lamps.svg"));
      templatePopulator.setMultilineText("supplier", form.getSupplierName())
          .setMultilineText("model", form.getModelName());
    } else {
      String templatePath;
      TemplateColour templateColour = TemplateColour.valueOf(form.getTemplateColour());
      TemplateSize templateSize = TemplateSize.valueOf(form.getTemplateSize());

      if(templateSize == TemplateSize.STANDARD) {
        if(templateColour == TemplateColour.COLOUR) {
          templatePath = "labels/lamps-light-sources/light-source-2021.svg";
        } else {
          templatePath = "labels/lamps-light-sources/light-source-bw-2021.svg";
        }
      } else {
        if(templateColour == TemplateColour.COLOUR) {
          templatePath = "labels/lamps-light-sources/light-source-small-2021.svg";
        } else {
          templatePath = "labels/lamps-light-sources/light-source-small-bw-2021.svg";
        }
      }

      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(templatePath));

      templatePopulator.setCondensingText("supplier", form.getSupplierName())
          .setCondensingText("model", form.getModelName())
          .setQrCode(form);
    }

    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setText("kwh", form.getEnergyConsumption())
        .asProcessedEnergyLabel(ProductMetadata.LAMPS_FULL, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsFormNoSupplierModel form, LegislationCategory legislationCategory) {
    TemplateColour templateColour = TemplateColour.valueOf(form.getTemplateColour());
    TemplatePopulator templatePopulator;

    if (templateColour == TemplateColour.COLOUR) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
          "labels/lamps-light-sources/excluding-name-model/lamps-excluding-name-model.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
          "labels/lamps-light-sources/excluding-name-model/lamps-excluding-name-model-bw.svg"));
    }

    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setText("kwh", form.getEnergyConsumption())
        .asProcessedEnergyLabelNoSupplier(ProductMetadata.LAMPS_RATING_CONSUMPTION, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsFormNoSupplierModelConsumption form, LegislationCategory legislationCategory) {
    TemplateColour templateColour = TemplateColour.valueOf(form.getTemplateColour());
    TemplatePopulator templatePopulator;

    if (templateColour == TemplateColour.COLOUR) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
          "labels/lamps-light-sources/excluding-name-model-consumption/lamps-excluding-name-model-consumption.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
          "labels/lamps-light-sources/excluding-name-model-consumption/lamps-excluding-name-model-consumption-bw.svg"));
    }

    return templatePopulator
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabelNoSupplier(ProductMetadata.LAMPS_RATING, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(LampsFormPackagingArrow form, LegislationCategory legislationCategory) {
    TemplateColour templateColour = TemplateColour.valueOf(form.getTemplateColour());
    TemplatePopulator templatePopulator;
    String templatePath = null;

    if(LightSourceArrowOrientation.valueOf(form.getLabelOrientation()) == LightSourceArrowOrientation.LEFT) {
      if(templateColour == TemplateColour.COLOUR) {
        templatePath = "light-source-packaging-arrow-left.svg";
      } else {
        templatePath = "light-source-packaging-arrow-bw-left.svg";
      }
    } else {
      if(templateColour == TemplateColour.COLOUR) {
        templatePath = "light-source-packaging-arrow-right.svg";
      } else {
        templatePath = "light-source-packaging-arrow-bw-right.svg";
      }
    }

    templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/lamps-light-sources/packaging-arrow/"+templatePath));

    return templatePopulator
        .transformPackagingArrow(RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabelLampsPackagingArrow(ProductMetadata.LAMPS_PACKAGING_ARROW, form);
  }

}
