package uk.gov.beis.els.categories.washingmachines.service;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.categories.common.ProcessedEnergyLabelDocument;
import uk.gov.beis.els.categories.washingmachines.model.WasherDryerForm;
import uk.gov.beis.els.categories.washingmachines.model.WashingMachinesForm;
import uk.gov.beis.els.model.LegislationCategory;
import uk.gov.beis.els.model.ProductMetadata;
import uk.gov.beis.els.model.RatingClass;
import uk.gov.beis.els.model.RatingClassRange;
import uk.gov.beis.els.model.SelectableLegislationCategory;
import uk.gov.beis.els.service.TemplateParserService;
import uk.gov.beis.els.service.TemplatePopulator;

@Service
public class WashingMachinesService {

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_PRE_MARCH_2021 = SelectableLegislationCategory.preMarch2021(
      RatingClassRange.of(RatingClass.APPP, RatingClass.D)
  );

  public static final SelectableLegislationCategory LEGISLATION_CATEGORY_POST_MARCH_2021 = SelectableLegislationCategory.postMarch2021();

  public static final List<SelectableLegislationCategory> LEGISLATION_CATEGORIES = new ImmutableList.Builder<SelectableLegislationCategory>()
      .add(LEGISLATION_CATEGORY_PRE_MARCH_2021)
      .add(LEGISLATION_CATEGORY_POST_MARCH_2021)
      .build();

  public static final RatingClassRange SPIN_DRYING_CLASS_RANGE = RatingClassRange.of(RatingClass.A, RatingClass.G);
  public static final RatingClassRange NOISE_EMISSIONS_CLASS_RANGE = RatingClassRange.of(RatingClass.A, RatingClass.D);


  private final TemplateParserService templateParserService;

  @Autowired
  public WashingMachinesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public ProcessedEnergyLabelDocument generateHtml(WashingMachinesForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator;
    if (legislationCategory.equals(LEGISLATION_CATEGORY_PRE_MARCH_2021)) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/washing-machines/washing-machines-2010.svg"));
      templatePopulator
          .setText("kwhAnnum", form.getAnnualEnergyConsumption())
          .setText("lAnnum", form.getAnnualWaterConsumption())
          .setText("kg", form.getCapacity())
          .setText("washDb", form.getWashingNoiseEmissions())
          .setText("spinDb", form.getSpinningNoiseEmissions());
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/washing-machines/washing-machines-2021.svg"));
      templatePopulator
          .setQrCode(form.getQrCodeUrl())
          .setText("kwh100cycles", form.getEnergyConsumptionPer100Cycles())
          .setText("kg", form.getEcoRatedCapacity())
          .setHoursMinutes("duration", form.getProgrammeDurationHours(), form.getProgrammeDurationMinutes())
          .setText("lCycle", form.getWaterConsumptionPerCycle())
          .setText("db", form.getNoiseEmissionValue())
          .applyRatingCssClass("noiseClass", RatingClass.valueOf(form.getNoiseEmissionClass()));
    }

    return templatePopulator
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .applyRatingCssClass("spinClass", RatingClass.valueOf(form.getSpinDryingEfficiencyRating()))
        .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .asProcessedEnergyLabel(ProductMetadata.WASHING_MACHINES, form);
  }

  public ProcessedEnergyLabelDocument generateHtml(WasherDryerForm form, LegislationCategory legislationCategory) {

    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/washing-machines/washer-dryers-2021.svg"));

    return templatePopulator
        .setQrCode(form.getQrCodeUrl())
        .setMultilineText("supplier", form.getSupplierName())
        .setMultilineText("model", form.getModelName())
        .setRatingArrow("completeCycleRating", RatingClass.valueOf(form.getCompleteCycleEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setRatingArrow("washCycleRating", RatingClass.valueOf(form.getWashingCycleEfficiencyRating()), legislationCategory.getPrimaryRatingRange())
        .setText("completeCycleKwh100cycles", form.getCompleteCycleEnergyConsumption())
        .setText("washCycleKwh100cycles", form.getWashingCycleEnergyConsumption())
        .setText("completeCycleKg", form.getCompleteCycleCapacity())
        .setText("washCycleKg", form.getWashingCycleCapacity())
        .setText("completeCycleL", form.getCompleteCycleWaterConsumption())
        .setText("washCycleL", form.getWashingCycleWaterConsumption())
        .setHoursMinutes("completeCycleDuration", form.getCompleteCycleDurationHours(), form.getCompleteCycleDurationMinutes())
        .setHoursMinutes("washCycleDuration", form.getWashingCycleDurationHours(), form.getWashingCycleDurationMinutes())
        .applyRatingCssClass("spinClass", RatingClass.valueOf(form.getSpinDryingEfficiencyRating()))
        .setText("db", form.getNoiseEmissionValue())
        .applyRatingCssClass("noiseClass", RatingClass.valueOf(form.getNoiseEmissionClass()))
        .asProcessedEnergyLabel(ProductMetadata.WASHING_MACHINES_WASHER_DRYER, form);
  }

}
