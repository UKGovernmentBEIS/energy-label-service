package uk.gov.beis.els.categories.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

  private static final Map<String, LoadProfile> ENUM_MAP;
  private final String displayName;

  LoadProfile(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  static {
    Map<String, LoadProfile> map = new HashMap<String, LoadProfile>();
    for (LoadProfile instance : LoadProfile.values()) {
      map.put(instance.getDisplayName().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static LoadProfile getEnum(String loadProfile) {
    LoadProfile result = ENUM_MAP.get(loadProfile.toLowerCase());
    if (result == null) {
      result = LoadProfile.valueOf(loadProfile);
    }
    return result;
  }
}
