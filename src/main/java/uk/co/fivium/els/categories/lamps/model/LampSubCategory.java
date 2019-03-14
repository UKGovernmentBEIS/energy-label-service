package uk.co.fivium.els.categories.lamps.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.lamps.controller.LampsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum LampSubCategory implements Category {

  LAMPS("Label with supplier's name/trademark, model identifier, energy rating and weighted energy consumption", ReverseRouter.route(on(LampsController.class).renderLamps(null))),
  LAMPS_EX_NAME_MODEL("Label with energy rating and weighted energy consumption only",
      ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null))),
  LAMPS_EX_NAME_MODEL_CONSUMPTION("Label with energy rating only",
      ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null)));

  public static String getCategoryQuestionText() {
    return "What type of label do you need for this lamp?";
  }

  public static String getNoSelectionErrorMessage() {
    return "Select a type of label";
  }

  private final String displayName;
  private final String nextStateUrl;

  LampSubCategory(String displayName, String nextStateUrl) {
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
