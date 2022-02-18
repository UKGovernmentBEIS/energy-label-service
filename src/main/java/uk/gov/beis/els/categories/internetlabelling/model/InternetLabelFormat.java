package uk.gov.beis.els.categories.internetlabelling.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import uk.gov.beis.els.model.Displayable;

public enum InternetLabelFormat implements Displayable {
  JPEG(".jpg", "JPG"),
  PNG(".png", "PNG");

  private static final Map<String, InternetLabelFormat> ENUM_MAP;
  private final String fileExtension;
  private final String displayName;

  InternetLabelFormat(String fileExtension, String displayName) {
    this.fileExtension = fileExtension;
    this.displayName = displayName;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  static {
    Map<String, InternetLabelFormat> map = new HashMap<String, InternetLabelFormat>();
    for (InternetLabelFormat instance : InternetLabelFormat.values()) {
      map.put(instance.getDisplayName().toLowerCase(), instance);
    }
    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static InternetLabelFormat getEnum(String internetLabelClass) {
    InternetLabelFormat result = ENUM_MAP.get(internetLabelClass.toLowerCase());
    if (result == null) {
      result = InternetLabelFormat.valueOf(internetLabelClass);
    }
    return result;
  }
}
