package uk.co.fivium.els.categories.waterheaters.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.waterheaters.controller.WaterHeatersController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum WaterHeaterSubCategory implements Category {

  HEAT_PUMP_WATER_HEATERS("Heat pump water heaters",
    ReverseRouter.route(on(WaterHeatersController.class).renderHeatPumpWaterHeaters(null)));

  public static String getCategoryQuestionText() {
    return "What type of water heater or storage tank do you need a label for?";
  }

  public static String getNoSelectionErrorMessage() {
    return "Select a type of water heater or storage tank";
  }

  private final String displayName;
  private final String nextStateUrl;

  WaterHeaterSubCategory(String displayName, String nextStateUrl) {
    this.displayName = displayName;
    this.nextStateUrl = nextStateUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getNextStateUrl() {
    return nextStateUrl;
  }

  @Override
  public String getName() {
    return this.name();
  }

}
