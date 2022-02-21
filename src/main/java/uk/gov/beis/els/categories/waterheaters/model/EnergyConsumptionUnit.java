package uk.gov.beis.els.categories.waterheaters.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import uk.gov.beis.els.model.Displayable;

public enum EnergyConsumptionUnit implements Displayable {

  KWH("Electricity consumption as kWh/annum"),
  GJ("Fuel consumption as GJ/annum"),
  BOTH("Both electricity consumption as kWh/annum and fuel consumption as GJ/annum");

  private static final Map<String, EnergyConsumptionUnit> ENUM_MAP;
  private final String displayName;

  EnergyConsumptionUnit(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  static {
    Map<String, EnergyConsumptionUnit> map = new HashMap<String, EnergyConsumptionUnit>();
    for (EnergyConsumptionUnit instance : EnergyConsumptionUnit.values()) {
      map.put(instance.getDisplayName().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static EnergyConsumptionUnit getEnum(String energyConsumptionUnit) {
    EnergyConsumptionUnit result = ENUM_MAP.get(energyConsumptionUnit.toLowerCase());
    if (result == null) {
      result = EnergyConsumptionUnit.valueOf(energyConsumptionUnit);
    }
    return result;
  }
}
