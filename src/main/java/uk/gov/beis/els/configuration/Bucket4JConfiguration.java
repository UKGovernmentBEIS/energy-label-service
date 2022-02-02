package uk.gov.beis.els.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Bucket4JConfiguration {

  @Bean
  public Bucket getBucket() {
    Bandwidth limit = Bandwidth.simple(20, Duration.ofMinutes(1));
    return Bucket.builder()
        .addLimit(limit)
        .build();
  }
}
