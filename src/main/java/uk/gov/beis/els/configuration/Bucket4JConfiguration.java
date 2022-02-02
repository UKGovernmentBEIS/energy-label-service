package uk.gov.beis.els.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Bucket4JConfiguration {

  @Bean
  public Bucket getBucket() {
    // Limits the API requests to 20 requests in a single minute
    Refill refill = Refill.intervally(20, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(20, refill);
    return Bucket.builder()
        .addLimit(limit)
        .build();
  }
}
