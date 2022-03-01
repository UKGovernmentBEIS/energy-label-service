package uk.gov.beis.els.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "api.v1.rate-limit")
@Configuration
public class RateLimitConfiguration {

  private String capacity;
  private String timeValue;
  private String timeUnit;

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public String getTimeValue() {
    return timeValue;
  }

  public void setTimeValue(String timeValue) {
    this.timeValue = timeValue;
  }

  public String getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(String timeUnit) {
    this.timeUnit = timeUnit;
  }
}
