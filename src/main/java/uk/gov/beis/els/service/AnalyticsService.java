package uk.gov.beis.els.service;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.beis.els.model.GoogleAnalyticsEventCategory;
import uk.gov.beis.els.model.GoogleAnalyticsEventParams;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AnalyticsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsService.class);

  private static final String GOOGLE_ANALYTICS_ENDPOINT = "https://www.google-analytics.com/mp/collect?api_secret={api_secret}&measurement_id={measurement_id}";

  private final boolean analyticsEnabled;
  private final int connectionTimeoutMs;
  private final String apiSecret;
  private final String measurementId;

  public AnalyticsService(@Value("${app.enable_google_analytics}") boolean analyticsEnabled,
                          @Value("${app.analytics_connection_timeout_ms}") int connectionTimeoutMs,
                          @Value("${app.analytics_api_secret}") String apiSecret,
                          @Value("${app.analytics_measurement_id}") String measurementId) {
    this.analyticsEnabled = analyticsEnabled;
    this.connectionTimeoutMs = connectionTimeoutMs;
    this.apiSecret = apiSecret;
    this.measurementId = measurementId;
  }

  public void sendGoogleAnalyticsEvent(String jsClientId, GoogleAnalyticsEventCategory eventCategory, GoogleAnalyticsEventParams eventParams) {
    if (analyticsEnabled) {
      try {
        // We might not get a client ID if the user has JavaScript disabled or blocks the Google Analytics JS.
        // In that case, we generate a random client ID so we can track this event, but it won't be linked to the rest of
        // the user's actions in the service
        String clientId = jsClientId;
        if (Strings.isNullOrEmpty(clientId)) {
          clientId = UUID.randomUUID().toString();
        }

        RestTemplate restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(connectionTimeoutMs))
            .setReadTimeout(Duration.ofMillis(connectionTimeoutMs))
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> event = new HashMap<>();
        event.put("name", eventCategory.getDisplayValue());
        event.put("params", eventParams.getParamsAsMap());

        Map<String, Object> map = new HashMap<>();

        // See https://developers.google.com/analytics/devguides/collection/protocol/ga4/sending-events?client_type=gtag for information on the
        // params we're sending
        map.put("client_id", clientId);
        map.put("events", new Map[]{event});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_ANALYTICS_ENDPOINT, request, String.class, apiSecret, measurementId);

        if (!response.getStatusCode().is2xxSuccessful()) {
          LOGGER.error("Non 2xx code returned for google analytics event send (was {}). Response was still served to user.", response.getStatusCodeValue());
        }

      } catch (Exception e) {
        LOGGER.error("Error sending google analytics event. Response was still served to user", e);
      }
    }

  }

}
