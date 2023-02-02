package uk.gov.beis.els.api.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import java.util.TreeMap;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("rawtypes")
public class OpenApiConfig {

  @Bean
  public OpenAPI configureOpenApi() {
    return new OpenAPI()
      .info(
        new Info()
            .title("Create an energy label")
            .description("Create a label for an energy-related product (ErP) that you make or sell to UK customers, for example white goods, boilers and other household appliances.")
            .version("v1.1")
      );
  }
  @Bean
  public OpenApiCustomiser sortSchemasAlphabetically() {
    return openApi -> {
      Map<String, Schema> schemas = openApi.getComponents().getSchemas();
      openApi.getComponents().setSchemas(new TreeMap<>(schemas));
    };
  }

}
