package uk.gov.beis.els.api.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenApiService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiService.class);
  private final ServletWebServerApplicationContext context;
  private OpenAPI openAPISpec;

  public OpenApiService(ServletWebServerApplicationContext context) {
    this.context = context;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void getOpenApiResponse() {
    String openApiUrl = String.format("http://localhost:%s/v3/api-docs", context.getWebServer().getPort());
    LOGGER.info("Parsing OpenAPI spec from {}", openApiUrl);
    RestTemplate restTemplate = new RestTemplate();
    openAPISpec = restTemplate.getForEntity(openApiUrl, OpenAPI.class).getBody();
    LOGGER.info("OpenAPI spec parsed");
  }

  public OpenAPI getOpenAPISpec() {
    return openAPISpec;
  }
}
