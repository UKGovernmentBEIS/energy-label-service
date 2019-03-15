package uk.co.fivium.els.categories.householdrefrigeratingappliances.model;

import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.householdrefrigeratingappliances.controller.HouseholdRefrigeratingAppliancesController;
import uk.co.fivium.els.mvc.ReverseRouter;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public enum HouseholdRefrigeratingApplianceSubCategory implements Category {

  FRIDGES_FREEZERS("Household fridges and freezers",
    ReverseRouter.route(on(HouseholdRefrigeratingAppliancesController.class).renderFridgesFreezers(null))),
  WINE_STORAGE_APPLIANCES("Wine storage appliances",
    ReverseRouter.route(on(HouseholdRefrigeratingAppliancesController.class).renderWineStorageAppliances(null)));

  public static String getCategoryQuestionText() {
    return "What type of household refrigerating appliance do you need a label for?";
  }

  public static String getNoSelectionErrorMessage() {
    return "Select a type of household refrigerating appliance";
  }

  private final String displayName;
  private final String nextStateUrl;

  HouseholdRefrigeratingApplianceSubCategory(String displayName, String nextStateUrl) {
    this.displayName = displayName;
    this.nextStateUrl = nextStateUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getNextStateUrl() {
    return nextStateUrl;
  }

  public String getName() {
    return this.name();
  }

}
