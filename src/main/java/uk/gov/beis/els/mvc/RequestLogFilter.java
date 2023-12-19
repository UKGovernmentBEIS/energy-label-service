package uk.gov.beis.els.mvc;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RequestLogFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    var startTimeMs = System.currentTimeMillis();

    try {
      filterChain.doFilter(request, response);
    } finally {
      var queryString = request.getQueryString() == null ? "" : request.getQueryString();
      if (!queryString.isEmpty()) {
        queryString = "?" + queryString;
      }

      LOGGER.info(
          "Request: {} {}{}, time: {}, status: {}",
          request.getMethod(),
          request.getRequestURI(),
          queryString,
          System.currentTimeMillis() - startTimeMs,
          response.getStatus()
      );
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    var requestUri = request.getRequestURI();
    return requestUri.startsWith("/actuator")
        || requestUri.startsWith("/assets");
  }
}
