package uk.co.fivium.elp.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import uk.co.fivium.elp.categories.lamps.controller.LampsController;
import uk.co.fivium.elp.mvc.ReverseRouter;

public enum ProductCategory {

  AIR_CONDITIONERS("Air Conditioners", "reverse-route-here"),
  DISHWASHERS("Dishwashers", "reverse-route-here"),
  DOMESTIC_OVENS("Domestic ovens", "reverse-route-here"),
  FRIDGES_AND_FREEZERS("Fridges and freezers", "reverse-route-here"),
  LAMPS("Lamps", ReverseRouter.route(on(LampsController.class).renderLampSubCategories(null))),
  LOCAL_SPACE_HEATERS("Local space heaters", "reverse-route-here"),
  REFRIGERATED_STORAGE_CABINETS("Professional refrigerated storage cabinets", "reverse-route-here"),
  RANGE_HOODS("Range hoods", "reverse-route-here"),
  SOLID_FUEL_BOILERS("Solid fuel boilers", "reverse-route-here"),
  SPACE_HEATERS("Space heaters", "reverse-route-here"),
  TELEVISIONS("Televisions", "reverse-route-here"),
  TUMBLE_DRYERS("Tumble dryers", "reverse-route-here"),
  TYRES("Tyres", "reverse-route-here"),
  VENTILATION_UNITS("Ventilation units", "reverse-route-here"),
  WASHING_MACHINES("Washing machines", "reverse-route-here"),
  WATER_HEATERS("Water heaters", "reverse-route-here");

  private final String displayName;
  private final String nextSateUrl;

  ProductCategory(String displayName, String nextSateUrl) {
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
