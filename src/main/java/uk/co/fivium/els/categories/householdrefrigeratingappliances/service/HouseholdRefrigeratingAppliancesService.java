package uk.co.fivium.els.categories.householdrefrigeratingappliances.service;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.model.FridgesFreezersForm;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.model.WineStorageAppliancesForm;
import uk.co.fivium.els.model.LegislationCategory;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.service.TemplateParserService;
import uk.co.fivium.els.service.TemplatePopulator;

@Service
public class HouseholdRefrigeratingAppliancesService {

  public static final LegislationCategory LEGISLATION_CATEGORY_CURRENT = LegislationCategory.of(
    RatingClassRange.of(RatingClass.APPP, RatingClass.G));

  private final TemplateParserService templateParserService;

  @Autowired
  public HouseholdRefrigeratingAppliancesService(TemplateParserService templateParserService) {
    this.templateParserService = templateParserService;
  }

  public Document generateHtml(FridgesFreezersForm form) {
    TemplatePopulator templatePopulator;
    // This class is the range in which the 'short' label is used, with fewer ratings listed
    RatingClassRange shortRange = RatingClassRange.of(RatingClass.APPP, RatingClass.C);
    // If the rating provided in the form is within the range specified above
    if (shortRange.getApplicableRatings().contains(RatingClass.valueOf(form.getEfficiencyRating()))) {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/household-refrigerating-appliances/household-refrigerating-appliances-a+++-to-d.svg"));
    } else {
      templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/household-refrigerating-appliances/household-refrigerating-appliances-d-to-g.svg"));
    }

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("fridgeLitres", form.getNonRatedVolume())
      .setText("freezerLitres", form.getRatedVolume())
      .setText("db", form.getNoiseEmissions())
      .getPopulatedDocument();
  }

  public Document generateHtml(WineStorageAppliancesForm form) {
    TemplatePopulator templatePopulator = new TemplatePopulator(templateParserService.parseTemplate("labels/household-refrigerating-appliances/wine-storage-appliances.svg"));

    return templatePopulator
      .setRatingArrow("rating", RatingClass.valueOf(form.getEfficiencyRating()), LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange())
      .setMultilineText("supplier", form.getSupplierName())
      .setMultilineText("model", form.getModelName())
      .setText("kwhAnnum", form.getAnnualEnergyConsumption())
      .setText("bottleCapacity", form.getBottleCapacity())
      .setText("db", form.getNoiseEmissions())
      .getPopulatedDocument();
  }
}
