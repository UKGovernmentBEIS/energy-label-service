package uk.gov.beis.els.categories.refrigeratingappliances.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import uk.gov.beis.els.model.Displayable;

public enum FreezerStarRating implements Displayable {
  ONE_STAR("One star", "starRating1"),
  TWO_STAR("Two star", "starRating2"),
  THREE_STAR("Three star", "starRating3"),
  FOUR_STAR("Four star", "starRating4");

  private static final Map<String, FreezerStarRating> ENUM_MAP;
  private final String displayName;
  private final String templateStarRatingClassName;

  FreezerStarRating(String displayName, String templateStarRatingClassName) {
    this.displayName = displayName;
    this.templateStarRatingClassName = templateStarRatingClassName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  public String getTemplateStarRatingClassName() {
    return templateStarRatingClassName;
  }

  static {
    Map<String, FreezerStarRating> map = new HashMap<String, FreezerStarRating>();
    for (FreezerStarRating instance : FreezerStarRating.values()) {
      map.put(instance.getDisplayName().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static FreezerStarRating getEnum(String freezerStarRating) {
    FreezerStarRating result = ENUM_MAP.get(freezerStarRating.toLowerCase());
    if (result == null) {
      result = FreezerStarRating.valueOf(freezerStarRating);
    }
    return result;
  }
}
