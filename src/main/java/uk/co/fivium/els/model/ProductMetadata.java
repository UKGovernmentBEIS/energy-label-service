package uk.co.fivium.els.model;

public enum ProductMetadata {

  AC_COOLING_ONLY_NON_DUCT("Air conditioners - Cooling-only ductless air conditioners", "Air conditioners"),
  AC_COOLING_ONLY_DUCT("Air conditioners - Cooling-only ducted air conditioners", "Air conditioners"),
  AC_HEATING_ONLY_NON_DUCT("Air conditioners - Heating-only ductless air conditioners", "Air conditioners"),
  AC_HEATING_ONLY_DUCT("Air conditioners - Heating-only ducted air conditioners", "Air conditioners"),
  AC_REVERSIBLE_NON_DUCT("Air conditioners - Reversible ductless air conditioners", "Air conditioners"),
  AC_REVERSIBLE_DUCT("Air conditioners - Reversible ducted air conditioners", "Air conditioners"),

  DISHWASHERS("Dishwashers", "Dishwashers"),

  OVENS_ELECTRIC("Ovens - Electric ovens", "Ovens"),
  OVENS_GAS("Ovens - Gas ovens", "Ovens"),

  LAMPS_FULL("Lamps - Label with supplier's name, Heating identification code, rating and energy consumption", "Lamps"),
  LAMPS_RATING_CONSUMPTION("Lamps - Label with energy rating and weighted energy consumption only", "Lamps"),
  LAMPS_RATING("Lamps - Label with energy rating only", "Lamps");

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
