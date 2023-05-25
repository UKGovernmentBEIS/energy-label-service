package uk.gov.beis.els.model;

import java.util.HashMap;
import java.util.Map;

public class GoogleAnalyticsEventParams {
  private final Map<String, String> params;

  public GoogleAnalyticsEventParams() {
    params = new HashMap<>();
  }

  public void addParam(String name, String value) throws RuntimeException {
    if(value == null) {
      // Null parameter values will cause the whole event to be rejected by GA, so discard them
      return;
    }

    if(name.length() > 40) {
      // Max param name GA will accept is 40 characters. Error if it's more than that because this is developer error (param names are hardcoded)
      throw new RuntimeException(String.format("Google Analytics param name must be 40 characters or fewer. Actual length: %s, param name '%s'", name.length(), name));
    }

    if(value.length() > 100) {
      // Value is more likely to be user-supplied (eg manufacturer name), so cut it down
      value = value.substring(0, 100);
    }

    this.params.put(name, value);
  }

  public Map<String, String> getParamsAsMap() {
    return this.params;
  }
}
