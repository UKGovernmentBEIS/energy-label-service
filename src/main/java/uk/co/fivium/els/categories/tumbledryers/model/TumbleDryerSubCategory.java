package uk.co.fivium.els.categories.tumbledryers.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.tumbledryers.controller.TumbleDryersController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum TumbleDryerSubCategory {

  AIR_VENTED_TUMBLE_DRYERS("Air vented tumble dryers", ReverseRouter.route(on(TumbleDryersController.class).renderAirVentedTumbleDryers(null))),
  CONDENSER_TUMBLE_DRYERS("Condenser tumble dryers", ReverseRouter.route(on(TumbleDryersController.class).renderCondenserTumbleDryers(null))),
  GAS_FIRED_TUMBLE_DRYERS("Gas-fired tumble dryers", ReverseRouter.route(on(TumbleDryersController.class).renderGasFiredTumbleDryers(null)));

  private final String displayName;
  private final String nextStateUrl;

  TumbleDryerSubCategory(String displayName, String nextStateUrl) {
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
