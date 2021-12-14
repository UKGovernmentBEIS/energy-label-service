package uk.gov.beis.els.api.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class OpenApiConfig {

  @Bean
  public Docket configureDocket() {
    return new Docket(DocumentationType.OAS_30)
        .select()
          .paths(PathSelectors.ant("/api/v1/**"))
          .build()
        .useDefaultResponseMessages(false)
        .apiInfo(new ApiInfoBuilder()
            .title("Create an energy label")
            .description("Create a label for an energy-related product (ErP) that you make or sell to UK customers, for example white goods, boilers and other household appliances.")
            .build()
        );
  }

}
