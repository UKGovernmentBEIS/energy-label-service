package uk.gov.beis.els.categories.prorefrigeratedcabinets.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import uk.gov.beis.els.model.Displayable;

public enum ClimateClass implements Displayable {
  THREE("3", "climateClass3"),
  FOUR("4", "climateClass4"),
  FIVE("5", "climateClass5");

  private static final Map<String, ClimateClass> ENUM_MAP;
  private final String displayName;
  private final String svgClass;

  ClimateClass(String displayName, String svgClass) {
    this.displayName = displayName;
    this.svgClass = svgClass;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  public String getSvgClass() {
    return svgClass;
  }

  static {
    Map<String, ClimateClass> map = new HashMap<String, ClimateClass>();
    for (ClimateClass instance : ClimateClass.values()) {
      map.put(instance.getDisplayName().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static ClimateClass getEnum(String climateClass) {
    ClimateClass result = ENUM_MAP.get(climateClass.toLowerCase());
    if (result == null) {
      result = ClimateClass.valueOf(climateClass);
    }
    return result;
  }
}
