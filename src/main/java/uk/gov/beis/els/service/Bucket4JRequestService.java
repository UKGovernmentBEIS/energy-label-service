package uk.gov.beis.els.service;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bucket4JRequestService {

  private static final Logger LOGGER = LoggerFactory.getLogger(Bucket4JRequestService.class);

  private final HttpServletRequest request;

  @Autowired
  public Bucket4JRequestService(HttpServletRequest request) {
    this.request = request;
  }


  /**
   * Checks the X-Forwarded-For header sent after load balancing and returns the last IP address in the list
   * This will be the origin IP address of the request.
   * This is used as the unique identifier for the rate-limiting of API requests so that a single origin IP can't make too many requests at one time.
   * The rate-limiting amount and time frame are controlled in the application.properties file.
   * @return the last IP address found in the X-Forwarded-For request header
   */
  public String getRequestIpAddress() {
    String ipAddresses = request.getHeader("X-Forwarded-For");
    if (ipAddresses != null) {
      List<String> ipAddressesSplit = Arrays.asList(ipAddresses.split("\\s*,\\s*"));
      if (ipAddressesSplit.size() == 1) {
        return ipAddressesSplit.get(0);
      } else if (ipAddressesSplit.size() > 1) {
        String lastIpAddress = Iterables.getLast(ipAddressesSplit);
        LOGGER.warn("Request for " + request.getRequestURI() + " found with multiple IP addresses: " + ipAddresses + ". Origin IP determined to be " + lastIpAddress);
        return lastIpAddress;
      } else {
        throw new RuntimeException("Request for " + request.getRequestURI() + " found with no IP addresses in the X-Forwarded-For header");
      }
    }
    else {
      throw new RuntimeException("Request for " + request.getRequestURI() + " found with no IP addresses in the X-Forwarded-For header");
    }
  }
}
