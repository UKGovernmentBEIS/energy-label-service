package uk.gov.beis.els.categories.televisions.service;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.televisions.model.TelevisionsForm;
import uk.gov.beis.els.model.InternetLabelTemplate;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class TelevisionsService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
      RatingClassRange.of(RatingClass.A, RatingClass.G),
      RatingClassRange.of(RatingClass.A, RatingClass.G), // HDR energy rating
      InternetLabelTemplate.RESCALED
  );

  private final TemplateParserService templateParserService;

  @Autowired
  public TelevisionsService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(TelevisionsForm form) {

    TemplatePopulator templatePopulator = new TemplatePopulator(
        templateParserService.parseTemplate("labels/televisions-electronic-displays/electronic-displays-2021.svg"))
        .setQrCode(form.getQrCodeUrl())
        .setRatingArrow("rating", RatingClass.getEnum(form.getEfficiencyRatingSdr()),
            LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
        .setText("kwh1000hSdr", form.getEnergyConsumptionSdr1000h())
        .setText("pxHorizontal", form.getHorizontalPixels())
        .setText("pxVertical", form.getVerticalPixels())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setText("cm", form.getScreenSizeCm())
        .setText("inch", form.getScreenSizeInch());

    if (BooleanUtils.isTrue(form.getIsHdr())) {
      templatePopulator
          .applyCssClassToId("hdrSection", "hasHdrSection")
          .applyRatingCssClass("hdrRating", RatingClass.getEnum(form.getEfficiencyRatingHdr()))
          .setText("kwh1000hHdr", form.getEnergyConsumptionHdr1000h());
    }

    return templatePopulator
        .asProcessedEnergyLabel(ProductMetadata.TV, form);
  }
}
