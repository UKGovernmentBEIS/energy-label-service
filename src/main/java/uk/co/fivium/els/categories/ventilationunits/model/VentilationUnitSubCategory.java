package uk.co.fivium.els.categories.ventilationunits.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.common.Category;
import uk.co.fivium.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum VentilationUnitSubCategory implements Category {

  UNIDIRECTIONAL_VENTILATION_UNITS("Unidirectional ventilation units",
    ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null))),
  BIDIRECTIONAL_VENTILATION_UNITS("Bidirectional ventilation units",
    ReverseRouter.route(on(VentilationUnitsController.class).renderBidirectionalVentilationUnits(null)));

  public static String getCategoryQuestionText() {
    return "What type of ventilation unit do you need a label for?";
  }

  public static String getNoSelectionErrorMessage() {
    return "Select a type of ventilation unit";
  }

  private final String displayName;
  private final String nextStateUrl;

  VentilationUnitSubCategory(String displayName, String nextStateUrl) {
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
