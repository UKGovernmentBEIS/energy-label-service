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
