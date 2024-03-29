package uk.gov.beis.els.service;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.beis.els.configuration.RateLimitConfiguration;

@Service
public class Bucket4JRequestService {

  private static final Logger LOGGER = LoggerFactory.getLogger(Bucket4JRequestService.class);

  private final HttpServletRequest request;
  private final RateLimitConfiguration rateLimitConfiguration;

  @Autowired
  public Bucket4JRequestService(HttpServletRequest request,
                                RateLimitConfiguration rateLimitConfiguration) {
    this.request = request;
    this.rateLimitConfiguration = rateLimitConfiguration;
  }


  /**
   * Get the client IP address from the X-Forwarded-For header.
   * The GOV.UK PaaS router removes the IPs of any upstream load balancer 'hops' from the XFF header. Therefore, the value of this header
   * is the just client IP. In the case of a pre-existing (i.e. spoofed) XFF header, the router will append the true client IP to the end of this list.
   * Therefore, in contrast to typical XFF parsing, to get the true client IP we use the last (or only) IP listed in the header.
   * This is used as the unique identifier for the rate-limiting of API requests so that a single origin IP can't make too many requests at one time.
   * The rate-limiting amount and time frame are controlled in the application.properties file.
   * @return the last IP address found in the X-Forwarded-For request header
   */
  public String getRequestIpAddress() {
    String ipAddresses = request.getHeader("X-Forwarded-For");
    if (ipAddresses != null) {
      List<String> ipAddressesSplit = Arrays.stream(ipAddresses.split("\\s*,\\s*"))
          .filter(i -> !i.isEmpty())
          .collect(Collectors.toList());
      if (ipAddressesSplit.size() == 1) {
        return ipAddressesSplit.get(0);
      } else if (ipAddressesSplit.size() > 1) {
        String lastIpAddress = Iterables.getLast(ipAddressesSplit);
        LOGGER.warn("Request for {} found with multiple IP addresses: {}. Origin IP determined to be {}", request.getRequestURI(), ipAddresses, lastIpAddress);
        return lastIpAddress;
      } else {
        throw new RuntimeException("Request for " + request.getRequestURI() + " found with no IP addresses in the X-Forwarded-For header");
      }
    }
    else {
      String hostHeader = request.getHeader("host");
      if (hostHeader != null && hostHeader.startsWith("localhost:")) {
        // Return a static value for loopback requests. All these requests will be considered to be from the same client.
        // This is required as the app requests the OpenAPI json from itself on start, and this request won't have a XFF header.
        return "localhost";
      }
      throw new RuntimeException("Request for " + request.getRequestURI() + " found with no IP addresses in the X-Forwarded-For header");
    }
  }

  public String getRateLimitCapacity() {
    return rateLimitConfiguration.getCapacity();
  }

  public String getRateLimitTimeValue() {
    return rateLimitConfiguration.getTimeValue();
  }

  public String getRateLimitTimeUnit() {
    String timeValue = getRateLimitTimeValue();
    String timeUnit = rateLimitConfiguration.getTimeUnit();
    return Integer.parseInt(timeValue) > 1
        ? timeUnit
        : StringUtils.remove(timeUnit, "s");
  }
}
