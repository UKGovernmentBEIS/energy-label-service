package uk.gov.beis.els.categories.common;

import java.util.Arrays;
import uk.gov.beis.els.model.Displayable;

public enum LoadProfile implements Displayable {
  XXXS("3XS"),
  XXS("XXS"),
  XS("XS"),
  S("S"),
  M("M"),
  L("L"),
  XL("XL"),
  XXL("XXL");

  private final String displayName;

  LoadProfile(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  public static LoadProfile getEnum(String loadProfile) {
    return Arrays.stream(LoadProfile.values())
        .filter(e -> e.getDisplayName().equals(loadProfile) || e.name().equals(loadProfile))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Can't resolve LoadProfile from string %s", loadProfile)));
  }
}
