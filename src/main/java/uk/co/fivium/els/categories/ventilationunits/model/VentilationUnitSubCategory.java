package uk.co.fivium.els.categories.ventilationunits.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum VentilationUnitSubCategory {

  UNIDIRECTIONAL_VENTILATION_UNITS("Unidirectional ventilation units",
    ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null))),
  BIDIRECTIONAL_VENTILATION_UNITS("Bidirectional ventilation units",
    ReverseRouter.route(on(VentilationUnitsController.class).renderBidirectionalVentilationUnits(null)));

  private final String displayName;
  private final String nextSateUrl;

  VentilationUnitSubCategory(String displayName, String nextSateUrl) {
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
