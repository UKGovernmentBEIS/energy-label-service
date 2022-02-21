package uk.gov.beis.els.categories.waterheaters.model;

public enum EnergyConsumptionUnit {

  KWH("Electricity consumption as kWh/annum"),
  GJ("Fuel consumption as GJ/annum"),
  BOTH("Both electricity consumption as kWh/annum and fuel consumption as GJ/annum");

  private final String displayName;

  EnergyConsumptionUnit(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
