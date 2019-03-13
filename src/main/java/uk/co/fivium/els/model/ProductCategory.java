package uk.co.fivium.els.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.els.categories.lamps.controller.LampsController;
import uk.co.fivium.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.co.fivium.els.categories.washingmachines.controller.WashingMachinesController;
import uk.co.fivium.els.mvc.ReverseRouter;

public enum ProductCategory {

  AIR_CONDITIONERS("Air Conditioners", "/not-yet-implemented"),
  DISHWASHERS("Dishwashers", "/not-yet-implemented"),
  DOMESTIC_OVENS("Domestic ovens", "/not-yet-implemented"),
  FRIDGES_AND_FREEZERS("Fridges and freezers", "/not-yet-implemented"),
  LAMPS("Lamps", ReverseRouter.route(on(LampsController.class).renderLampSubCategories(null))),
  LOCAL_SPACE_HEATERS("Local space heaters", "/not-yet-implemented"),
  REFRIGERATED_STORAGE_CABINETS("Professional refrigerated storage cabinets", "/not-yet-implemented"),
  RANGE_HOODS("Range hoods", "/not-yet-implemented"),
  SOLID_FUEL_BOILERS("Solid fuel boilers", "/not-yet-implemented"),
  SPACE_HEATERS("Space heaters", "/not-yet-implemented"),
  TELEVISIONS("Televisions", "/not-yet-implemented"),
  TUMBLE_DRYERS("Tumble dryers", "/not-yet-implemented"),
  TYRES("Tyres", "/not-yet-implemented"),
  VENTILATION_UNITS("Ventilation units", ReverseRouter.route(on(VentilationUnitsController.class).renderVentilationUnitsSubCategories(null))),
  WASHING_MACHINES("Washing machines", ReverseRouter.route(on(WashingMachinesController.class).renderWashingMachines(null))),
  WATER_HEATERS("Water heaters", "/not-yet-implemented");

  private final String displayName;
  private final String nextStateUrl;

  ProductCategory(String displayName, String nextStateUrl) {
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
