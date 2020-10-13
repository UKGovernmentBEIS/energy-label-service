package uk.gov.beis.els.model;

// TODO capture association to Form object via annotation.
public enum ProductMetadata {

  AC_COOLING_ONLY_NON_DUCT("Air conditioners - Cooling-only ductless air conditioners", "Air conditioners"),
  AC_COOLING_ONLY_DUCT("Air conditioners - Cooling-only ducted air conditioners", "Air conditioners"),
  AC_HEATING_ONLY_NON_DUCT("Air conditioners - Heating-only ductless air conditioners", "Air conditioners"),
  AC_HEATING_ONLY_DUCT("Air conditioners - Heating-only ducted air conditioners", "Air conditioners"),
  AC_REVERSIBLE_NON_DUCT("Air conditioners - Reversible ductless air conditioners", "Air conditioners"),
  AC_REVERSIBLE_DUCT("Air conditioners - Reversible ducted air conditioners", "Air conditioners"),

  SPACE_HEATER_BOILER("Space heaters - Boiler space heaters", "Space heaters"),
  SPACE_HEATER_BOILER_COMBI("Space heaters - Boiler combination heaters", "Space heaters"),
  SPACE_HEATER_COGEN("Space heaters - Cogeneration space heaters", "Space heaters"),
  SPACE_HEATER_HEAT_PUMP("Space heaters - Heat pump space heaters", "Space heaters"),
  SPACE_HEATER_HEAT_PUMP_COMBINATION("Space heaters - Heat pump combination heaters", "Space heaters"),
  SPACE_HEATER_LOW_TEMP("Space heaters - Low-temperature heat pump space heaters", "Space heaters"),
  SPACE_HEATER_PACKAGE("Space heaters - Packages of space heater, temperature control and solar device", "Space heaters"),
  SPACE_HEATER_PACKAGE_COMBINATION("Space heaters - Packages of combination heater, temperature control and solar device", "Space heaters"),

  DISHWASHERS("Dishwashers", "Dishwashers"),

  HRA_FRIDGE_FREEZER("Household refrigerating appliances - Fridges and freezers", "Refrigerating appliances"),
  HRA_WINE_STORAGE("Household refrigerating appliances - Wine storage appliances", "Refrigerating appliances"),

  LAMPS_FULL("Lamps - Label with supplier's name, identification code, rating and energy consumption", "Lamps"),
  LAMPS_RATING_CONSUMPTION("Lamps - Label with energy rating and weighted energy consumption only", "Lamps"),
  LAMPS_RATING("Lamps - Label with energy rating only", "Lamps"),

  LOCAL_SPACE_HEATERS("Local space heaters", "Local space heaters"),

  OVENS_ELECTRIC("Ovens - Electric ovens", "Ovens"),
  OVENS_GAS("Ovens - Gas ovens", "Ovens"),

  PRO_REFRIGERATED_CABINETS("Professional refrigerated storage cabinets", "Professional refrigerated storage cabinets"),

  ICE_CREAM_FREEZERS("Direct sales refrigerators - Ice cream freezers", "Refrigerators with direct sales function"),
  BEVERAGE_COOLERS("Direct sales refrigerators - Beverage coolers", "Refrigerators with direct sales function"),
  VENDING_MACHINES("Direct sales refrigerators - Vending machines", "Refrigerators with direct sales function"),
  DISPLAY_CABINETS("Direct sales refrigerators - Supermarket refrigerator/freezer cabinets or gelato-scooping cabinets", "Refrigerators with direct sales function"),

  RANGE_HOODS("Range hoods", "Range hoods"),

  SOLID_FUEL_BOILER("Solid fuel boilers and packages - Solid fuel boiler", "Solid fuel boiler and packages"),
  SOLID_FUEL_BOILER_PACKAGE("Solid fuel boilers and packages - Packages of solid fuel boiler, supplementary heaters, temperature controls and solar devices ", "Solid fuel boiler and packages"),

  TUMBLE_DRYERS_AIR_VENTED("Tumble dryers - Air vented tumble dryers", "Tumble dryers"),
  TUMBLE_DRYERS_CONDENSER("Tumble dryers - Condenser tumble dryers", "Tumble dryers"),
  TUMBLE_DRYERS_GAS_FIRED("Tumble dryers - Gas-fired tumble dryers", "Tumble dryers"),

  TV("Televisions and electronic displays", "Televisions and electronic displays"),

  VENTILATION_UNITS_UNIDIRECTIONAL("Ventilation units - Unidirectional ventilation units", "Ventilation units"),
  VENTILATION_UNITS_BIDIRECTIONAL("Ventilation units - Bidirectional ventilation units", "Ventilation units"),

  WASHING_MACHINES("Washing machines", "Washing machines"),

  WATER_HEATERS_CONVENTIONAL("Water heaters - Conventional water heaters", "Water heaters"),
  WATER_HEATERS_HEAT_PUMP("Water heaters - Heat pump water heaters", "Water heaters"),
  WATER_HEATERS_SOLAR("Water heaters - Solar water heaters", "Water heaters"),
  WATER_HEATERS_STORAGE_TANKS("Water heaters - Hot water storage tanks ", "Water heaters"),
  WATER_HEATERS_PACKAGE("Water heaters - Packages of water heater and solar device ", "Water heaters");



  private final String analyticsLabel;
  private final String productFileName;

  ProductMetadata(String analyticsLabel, String productFileName) {
    this.analyticsLabel = analyticsLabel;
    this.productFileName = productFileName;
  }

  public String getAnalyticsLabel() {
    return analyticsLabel;
  }

  public String getProductFileName() {
    return productFileName;
  }
}
