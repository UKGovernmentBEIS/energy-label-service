package uk.gov.beis.els.service;

import java.time.Duration;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;

@Service
public class AnalyticsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsService.class);

  private static final String GOOGLE_ANALYTICS_ENDPOINT = "https://www.google-analytics.com/collect";

  private boolean analyticsEnabled;
  private int connectionTimeoutMs;

  public AnalyticsService(@Value("${app.enable_google_analytics}") boolean analyticsEnabled, @Value("${app.analytics_connection_timeout_ms}") int connectionTimeoutMs) {
    this.analyticsEnabled = analyticsEnabled;
    this.connectionTimeoutMs = connectionTimeoutMs;
  }

  public void sendGoogleAnalyticsEvent(String jsClientId, GoogleAnalyticsEventCategory eventCategory, String action, String eventLabel) {
    if (analyticsEnabled) {
      try {
        // We might not get a client ID if the user has JavaScript disabled or blocks the Google Analytics JS.
        // In that case, we generate a random client ID so we can track this event, but it won't be linked to the rest of
        // the user's actions in the service
        String clientId = jsClientId;
        if (clientId.isEmpty()) {
          clientId = UUID.randomUUID().toString();
        }

        RestTemplate restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(connectionTimeoutMs))
            .setReadTimeout(Duration.ofMillis(connectionTimeoutMs))
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        // See https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#event for information on the
        // params we're sending
        map.add("v", "1");
        map.add("tid", "UA-136887405-1");
        map.add("cid", clientId);
        map.add("t", "event");
        map.add("ec", eventCategory.getDisplayValue());
        map.add("ea", action);
        map.add("el", eventLabel);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_ANALYTICS_ENDPOINT, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
          LOGGER.error("Non 2xx code returned for google analytics event send (was {}). Response was still served to user.", response.getStatusCodeValue());
        }

      } catch (Exception e) {
        LOGGER.error("Error sending google analytics event. Response was still served to user", e);
      }
    }

  }

}
