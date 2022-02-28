package uk.gov.beis.els.api.openapi;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.beis.els.api.common.ApiDocumentationController;
import uk.gov.beis.els.api.model.OperationWithSchema;
import uk.gov.beis.els.api.model.SchemaPropertyExample;
import uk.gov.beis.els.api.model.TagLink;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.util.StreamUtils;

@Service
public class OpenApiService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiService.class);
  private final ServletWebServerApplicationContext context;
  private OpenAPI openAPISpec;
  private Map<String, OperationWithSchema> operationMap;

  @Autowired
  public OpenApiService(ServletWebServerApplicationContext context) {
    this.context = context;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void parseOpenApi() {
    String openApiUrl = String.format("http://localhost:%s/v3/api-docs", context.getWebServer().getPort());
    LOGGER.info("Parsing OpenAPI spec from {}", openApiUrl);
    RestTemplate restTemplate = new RestTemplate();
    openAPISpec = restTemplate.getForEntity(openApiUrl, OpenAPI.class).getBody();
    operationMap = parseOperationMap();
    LOGGER.info("OpenAPI spec parsed");
  }

  public String getApiSpecUrl(HttpServletRequest request) {
    return getBaseUrl(request) + "/v3/api-docs/";
  }

  public String getBaseUrl(HttpServletRequest request) {
    return ServletUriComponentsBuilder.fromRequestUri(request)
        .replacePath(null)
        .build()
        .toUriString();
  }

  public String getAPIVersion() {
    return openAPISpec.getInfo().getVersion();
  }

  @SuppressWarnings("rawtypes")
  private Map<String, OperationWithSchema> parseOperationMap() {
    return openAPISpec.getPaths().entrySet().stream()
        .sorted(Comparator.comparing(e -> e.getValue().getPost().getSummary()))
        .collect(StreamUtils.toLinkedHashMap(Map.Entry::getKey, e -> {
          Operation operation = e.getValue().getPost();
          String tag = operation.getTags().stream().findFirst().orElseThrow(() -> new RuntimeException(String.format("No tag found for operation: %s", operation.getSummary())));
          String schemaRef = operation.getRequestBody().getContent().get("application/json").getSchema().get$ref();
          Schema schema = openAPISpec.getComponents().getSchemas().get(StringUtils.remove(schemaRef, "#/components/schemas/"));
          String example = getExampleJsonBody(schema);

          return new OperationWithSchema(operation, tag, schema, example);
        }));
  }

  /**
   * @param tag The tag for the current API documentation page, e.g. Air Conditioners
   * @return a map of the operation path and the individual operations for the given tag
   */
  public Map<String, OperationWithSchema> getOperationWithPathForTag(String tag) {
    return operationMap.entrySet()
        .stream().filter(e -> e.getValue().getTag().equals(tag))
        .collect(StreamUtils.toLinkedHashMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @SuppressWarnings("rawtypes")
  private String getExampleJsonBody(Schema schema) {
    String example = "";
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode objectNode = objectMapper.createObjectNode();
      Map<String, Schema<?>> properties = schema.getProperties();

      List<SchemaPropertyExample> schemaPropertyExamples = properties.entrySet().stream()
          .map(p -> new SchemaPropertyExample(
              p.getKey(),
              p.getValue().getExample() != null ? p.getValue().getExample().toString() : "")
          ).collect(Collectors.toList());

      for (SchemaPropertyExample schemaPropertyExample : schemaPropertyExamples) {
        objectNode.put(schemaPropertyExample.getPropertyName(), schemaPropertyExample.getExampleInput());
      }

      example = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    } catch (JsonProcessingException e) {
      LOGGER.warn("Could not create API request body example for {}. {}", schema.getName(), e.getMessage());
    }

    return example;
  }

  public List<TagLink> getTagLinks() {
    return openAPISpec.getTags().stream()
        .map(this::getTagLink)
        .sorted(Comparator.comparing(TagLink::getName))
        .collect(Collectors.toList());
  }

  private TagLink getTagLink(Tag tag) {
    return new TagLink(
        tag.getName(),
        ReverseRouter.route(on(ApiDocumentationController.class).getApiDocumentationForTag(tag.getName(), null))
    );
  }
}
