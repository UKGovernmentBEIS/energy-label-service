package uk.gov.beis.els.categories.refrigeratingappliances.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FreezerStarRating;
import uk.gov.beis.els.categories.refrigeratingappliances.model.FridgesFreezersForm;
import uk.gov.beis.els.categories.refrigeratingappliances.model.WineStorageAppliancesForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class RefrigeratingAppliancesService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_PRE_MARCH_2021 = SelectableLegislationCategory.preMarch2021(
      RatingClassRange.of(RatingClass.APPP, RatingClass.G)
  );

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_POST_MARCH_2021 = SelectableLegislationCategory.postMarch2021(
      RatingClassRange.of(RatingClass.A, RatingClass.D)  // Noise emission class
  );

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_PRE_MARCH_2021)
      .add(LEGISLATION_CATEGORY_POST_MARCH_2021)
      .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public RefrigeratingAppliancesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(FridgesFreezersForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator;
    if (legislationCategory.equals(LEGISLATION_CATEGORY_PRE_MARCH_2021)) {
      // This class is the range in which the 'short' label is used, with fewer ratings listed
      RatingClassRange shortRange = RatingClassRange.of(RatingClass.APPP, RatingClass.C);
      // If the rating provided in the form is within the range specified above
      if (shortRange.getApplicableRatings().contains(RatingClass.valueOf(form.getEfficiencyRating()))) {
        templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/household-refrigerating-appliances/household-refrigerating-appliances-a+++-to-d-2010.svg"));
      } else {
        templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/household-refrigerating-appliances/household-refrigerating-appliances-d-to-g-2010.svg"));
      }

      if (form.getRatedCompartmentPreMarch2021()) {
        templatePopulator
            .setText("freezerLitres", form.getRatedVolumePreMarch2021())
            .applyCssClassToId("starRating", FreezerStarRating.valueOf(form.getStarRatingPreMarch2021()).getTemplateStarRatingClassName());
      }
      else {
        templatePopulator
            .setText("freezerLitres", "-");
      }

      if (form.getNonRatedCompartmentPreMarch2021()) {
        templatePopulator
            .setText("fridgeLitres", form.getNonRatedVolumePreMarch2021());
      }
      else {
        templatePopulator
            .setText("fridgeLitres", "-");
      }
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/household-refrigerating-appliances/household-refrigerating-appliances-2021.svg"));

      if (form.getRatedCompartmentPostMarch2021()) {
        templatePopulator
            .setText("freezerLitres", form.getRatedVolumePostMarch2021())
            .applyCssClassToId("freezerSection", "hasFreezerSection");

        if(!form.getNonRatedCompartmentPostMarch2021()) {
          templatePopulator.setElementTranslate("freezerSection", 65.3, 0);
        }
      }

      if (form.getNonRatedCompartmentPostMarch2021()) {
        templatePopulator
            .setText("fridgeLitres", form.getNonRatedVolumePostMarch2021())
            .applyCssClassToId("fridgeSection", "hasFridgeSection");

        if(!form.getRatedCompartmentPostMarch2021()) {
          templatePopulator.setElementTranslate("fridgeSection", -63.5, 0);
        }
      }

      templatePopulator
          .setQrCode(form)
          .applyRatingCssClass("noiseClass", RatingClass.valueOf(form.getNoiseEmissionsClass()));
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("db", form.getNoiseEmissions())
      .asProcessedEnergyLabel(ProductMetadata.HRA_FRIDGE_FREEZER, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(WineStorageAppliancesForm form, LegislationCategory legislationCategory) {
    TemplatePopulator templatePopulator;

    if (legislationCategory.equals(LEGISLATION_CATEGORY_PRE_MARCH_2021)) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
          "labels/household-refrigerating-appliances/wine-storage-appliances-2010.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate(
          "labels/household-refrigerating-appliances/wine-storage-appliances-2021.svg"))
          .setQrCode(form)
          .applyRatingCssClass("noiseClass", RatingClass.valueOf(form.getNoiseEmissionsClass()));
    }

    return templatePopulator
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("bottleCapacity", form.getBottleCapacity())
      .setText("db", form.getNoiseEmissions())
      .asProcessedEnergyLabel(ProductMetadata.HRA_WINE_STORAGE, form);
  }
}
