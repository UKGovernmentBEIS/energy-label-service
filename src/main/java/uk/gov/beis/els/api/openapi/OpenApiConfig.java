package uk.gov.beis.els.api.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI configureOpenApi() {
    return new OpenAPI()
      .info(
        new Info()
            .title("Create an energy label")
            .description("Create a label for an energy-related product (ErP) that you make or sell to UK customers, for example white goods, boilers and other household appliances.")
            .version("v1")
      );
  }

}
