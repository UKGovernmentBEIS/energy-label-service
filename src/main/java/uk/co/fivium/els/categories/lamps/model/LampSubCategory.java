package uk.co.fivium.els.categories.lamps.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.lamps.controller.LampsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum LampSubCategory {

  LAMPS("Lamps", ReverseRouter.route(on(LampsController.class).renderLamps(null))),
  LAMPS_EX_NAME_MODEL("Lamps with suppliers name/trademark and model identifier included on the packaging",
      ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null))),
  LAMPS_EX_NAME_MODEL_CONSUMPTION("Lamps with suppliers name/trademark, model identifier and weighted energy consumption included on the packaging",
      ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null)));

  private final String displayName;
  private final String nextSateUrl;

  LampSubCategory(String displayName, String nextSateUrl) {
    this.displayName = displayName;
    this.nextSateUrl = nextSateUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getNextSateUrl() {
    return nextSateUrl;
  }
}
