package uk.gov.beis.els.api.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenApiService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiService.class);
  private OpenAPI openAPISpec;

  @EventListener(ApplicationReadyEvent.class)
  public void getOpenApiResponse() {
    String openApiUrl = "http://localhost:8080/v3/api-docs";
    LOGGER.info("Parsing OpenAPI spec from {}", openApiUrl);
    RestTemplate restTemplate = new RestTemplate();
    openAPISpec = restTemplate.getForEntity(openApiUrl, OpenAPI.class).getBody();
    LOGGER.info("OpenAPI spec parsed");
  }

  public OpenAPI getOpenAPISpec() {
    return openAPISpec;
  }
}
