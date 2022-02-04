package uk.gov.beis.els.categories.televisions.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class TelevisionsService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_PRE_MARCH_2021 = SelectableLegislationCategory.preMarch2021(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D)
  );

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_POST_MARCH_2021 = SelectableLegislationCategory.postMarch2021(
      RatingClassRange.of(RatingClass.A, RatingClass.G) // HDR energy rating
  );

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_PRE_MARCH_2021)
      .add(LEGISLATION_CATEGORY_POST_MARCH_2021)
      .build();

  private final TemplateParserService templateParserService;

  @Autowired
  public TelevisionsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(TelevisionsForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;

    if (legislationCategory.equals(LEGISLATION_CATEGORY_PRE_MARCH_2021)) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/televisions-electronic-displays/televisions.svg"));

      if (form.getPowerSwitch()) {
        templatePopulator.applyCssClassToId("powerSwitch", "hasPowerSwitch");
      }

      templatePopulator
          .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
          .setText("watt", form.getPowerConsumption())
          .setText("kwhAnnum", form.getAnnualEnergyConsumption());
    } else {
      templatePopulator = new TemplatePopulator(
          templateParserService.parseTemplate("labels/televisions-electronic-displays/electronic-displays-2021.svg"));

      templatePopulator
          .setQrCode(form.getQrCodeUrl())
          .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRatingSdr()),
              legislationCategory.getPrimaryRatingRange())
          .setText("kwh1000hSdr", form.getEnergyConsumptionSdr1000h())
          .setText("pxHorizontal", form.getHorizontalPixels())
          .setText("pxVertical", form.getVerticalPixels());

      if (BooleanUtils.isTrue(form.getIsHdr())) {
        templatePopulator
            .applyCssClassToId("hdrSection", "hasHdrSection")
            .applyRatingCssClass("hdrRating", RatingClass.valueOf(form.getEfficiencyRatingHdr()))
            .setText("kwh1000hHdr", form.getEnergyConsumptionHdr1000h());
      }
    }

    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("cm", form.getScreenSizeCm())
        .setText("inch", form.getScreenSizeInch())
        .asProcessedEnergyLabel(ProductMetadata.TV, form);
  }
}
