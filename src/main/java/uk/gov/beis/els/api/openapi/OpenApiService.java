package uk.gov.beis.els.api.openapi;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
import uk.gov.beis.els.api.model.TagLink;
import uk.gov.beis.els.mvc.ReverseRouter;
import uk.gov.beis.els.util.StreamUtils;

@Service
public class OpenApiService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiService.class);
  private final ServletWebServerApplicationContext context;
  private OpenAPI openAPISpec;

  @Autowired
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

  public String getApiSpecUrl(HttpServletRequest request) {
    return getBaseUrl(request) + "/v3/api-docs/";
  }

  public String getBaseUrl(HttpServletRequest request) {
    return ServletUriComponentsBuilder.fromRequestUri(request)
        .replacePath(null)
        .build()
        .toUriString();
  }

  /**
   * @param tag The tag for the current API documentation page, e.g. Air Conditioners
   * @return a map of the operation path and the individual operations for the given tag
   */
  @SuppressWarnings("rawtypes")
  public Map<String, OperationWithSchema> getOperationWithPathForTag(String tag) {
    // Get the operation list for the tag
    List<Operation> operationList = openAPISpec.getPaths().values().stream()
        .map(PathItem::getPost)
        .filter(post -> post.getTags().contains(tag))
        .sorted(Comparator.comparing(Operation::getSummary))
        .collect(Collectors.toList());

    // Get the schema associated with each operation
    List<OperationWithSchema> operationWithSchemaList = new ArrayList<>();

    for (Operation operation : operationList) {
      String schemaRef = operation.getRequestBody().getContent().get("application/json").getSchema().get$ref();
      Schema schema = openAPISpec.getComponents().getSchemas().get(StringUtils.remove(schemaRef, "#/components/schemas/"));
      operationWithSchemaList.add(new OperationWithSchema(operation, schema));
    }

    // Go back up the openapi spec chain and get the path for each operation in the list
    // There should only be one path per operationId
    // Add that path and operation (with schema) to the map we want to return
    Map<String, OperationWithSchema> operationWithPathMap = new HashMap<>();

    for (OperationWithSchema operationWithSchema : operationWithSchemaList) {
      String operationId = operationWithSchema.getOperation().getOperationId();

      Paths paths = openAPISpec.getPaths().entrySet().stream()
          .filter(p -> p.getValue().getPost().getOperationId().equals(operationId))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, Paths::new));

      String path = paths.entrySet().iterator().next().getKey();

      operationWithPathMap.put(path, operationWithSchema);
    }

    // Order the map by the operation summary and return it
    return operationWithPathMap.entrySet().stream()
        .sorted(Comparator.comparing(o -> o.getValue().getOperation().getSummary()))
        .collect(StreamUtils.toLinkedHashMap(Map.Entry::getKey, Map.Entry::getValue));
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
