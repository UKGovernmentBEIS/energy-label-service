package uk.gov.beis.els.categories.prorefrigeratedcabinets.model;

import java.util.Arrays;
import uk.gov.beis.els.model.Displayable;

public enum ClimateClass implements Displayable {
  THREE("3", "climateClass3"),
  FOUR("4", "climateClass4"),
  FIVE("5", "climateClass5");

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

  public static ClimateClass getEnum(String climateClass) {
    return Arrays.stream(ClimateClass.values())
        .filter(e -> e.getDisplayName().equals(climateClass) || e.name().equals(climateClass))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Can't resolve ClimateClass from string %s", climateClass)));
  }
}
