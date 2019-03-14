package uk.co.fivium.els.categories.waterheaters.model;

import uk.co.fivium.els.categories.waterheaters.controller.WaterHeatersController;
import uk.co.fivium.els.mvc.ReverseRouter;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public enum WaterHeaterSubCategory {

  HEAT_PUMP_WATER_HEATERS("Heat pump water heaters",
    ReverseRouter.route(on(WaterHeatersController.class).renderHeatPumpWaterHeaters(null)));

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
}
