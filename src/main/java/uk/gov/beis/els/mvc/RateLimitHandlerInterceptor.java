package uk.gov.beis.els.mvc;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
public class RateLimitHandlerInterceptor implements HandlerInterceptor {

  private final Bucket bucket;

  public RateLimitHandlerInterceptor(Bucket bucket) {
    this.bucket = bucket;
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
    if (probe.isConsumed()) {
      response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
      return true;
    } else {
      long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
      response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
      response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
          "You have exhausted your API Request Quota");
      return false;
    }
  }
}
