package uk.gov.beis.els.api.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.beis.els.api.common.ApiDocumentationController;
import uk.gov.beis.els.api.common.TagNotFoundException;
import uk.gov.beis.els.api.model.OperationWithSchema;
import uk.gov.beis.els.api.model.TagLink;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.util.StreamUtils;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Service
public class OpenApiService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiService.class);
  private final ServletWebServerApplicationContext context;
  private final ObjectMapper objectMapper;
  private OpenAPI openAPISpec;
  private Map<String, OperationWithSchema> operationMap;
  private String openApiJsonPath;

  @Autowired
  public OpenApiService(ServletWebServerApplicationContext context,
                        ObjectMapper objectMapper,
                        @Value("${springdoc.api-docs.path}") String openApiJsonPath) {
    this.context = context;
    this.objectMapper = objectMapper.setDefaultPrettyPrinter(new JsonPrettyPrinter());
    this.openApiJsonPath = openApiJsonPath;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void parseOpenApi() {
    String openApiUrl = String.format("http://localhost:%s%s", context.getWebServer().getPort(), openApiJsonPath);
    LOGGER.info("Parsing OpenAPI spec from {}", openApiUrl);
    RestTemplate restTemplate = new RestTemplate();
    openAPISpec = restTemplate.getForEntity(openApiUrl, OpenAPI.class).getBody();
    operationMap = parseOperationMap();
    LOGGER.info("OpenAPI spec parsed");
  }

  public String getApiSpecUrl(HttpServletRequest request) {
    return getBaseUrl(request) + openApiJsonPath;
  }

  public String getBaseUrl(HttpServletRequest request) {
    UriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequestUri(request)
        .replacePath(null);

    String xForwardedProto = request.getHeader("X-Forwarded-Proto");
    if (xForwardedProto != null) {
      builder.scheme(xForwardedProto);
    }

    return builder.build()
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
          String example = getExampleJsonBody(schema, schemaRef);

          return new OperationWithSchema(operation, tag, schema, example);
        }));
  }

  /**
   * @param tag The tag for the current API documentation page, e.g. Air Conditioners
   * @return a map of the operation path and the individual operations for the given tag
   */
  public Map<String, OperationWithSchema> getOperationWithPathForTag(String tag) {
    Map<String, OperationWithSchema> operationWithSchemaMap = operationMap.entrySet()
        .stream().filter(e -> e.getValue().getTag().equals(tag))
        .collect(StreamUtils.toLinkedHashMap(Map.Entry::getKey, Map.Entry::getValue));

    if (operationWithSchemaMap.isEmpty()) {
      throw new TagNotFoundException(String.format("Tag not found with name: %s", tag));
    } else {
      return operationWithSchemaMap;
    }
  }

  @SuppressWarnings("rawtypes")
  private String getExampleJsonBody(Schema schema, String schemaRef) {
    try {
      ObjectNode objectNode = objectMapper.createObjectNode();
      Map<String, Schema<?>> properties = schema.getProperties();

      properties.forEach((key, value) ->  {
        if (value.getExample() == null) {
          objectNode.put(key, "");
        } else {
          switch (value.getType()) {
            case "integer":
              objectNode.put(key, (int) value.getExample());
              break;
            case "number":
              objectNode.put(key, (double) value.getExample());
              break;
            case "boolean":
              objectNode.put(key, (boolean) value.getExample());
              break;
            default:
              objectNode.put(key, value.getExample().toString());
              break;
          }
        }
      });

      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    } catch (JsonProcessingException e) {
      LOGGER.warn("Could not create API request body example for schema ref: {}. {}", schemaRef, e.getMessage());
      return "";
    }
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
