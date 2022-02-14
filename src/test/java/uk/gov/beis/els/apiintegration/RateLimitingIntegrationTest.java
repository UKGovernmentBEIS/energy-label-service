package uk.gov.beis.els.apiintegration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.beis.els.service.Bucket4JRequestService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = RateLimitingIntegrationTest.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("bucket4j-integration-test")
public class RateLimitingIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  protected WebApplicationContext context;

  @Autowired
  protected Bucket4JRequestService bucket4JRequestService;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();
  }

  //TODO fix integration test: raised ELG-39 for this
  @Test
  public void withSingleIpAddress() throws Exception {

//    String apiContent = "{\n" +
//        "  \"supplierName\": \"string\",\n" +
//        "  \"modelName\": \"string\",\n" +
//        "  \"efficiencyRating\": \"APPP\",\n" +
//        "  \"volume\": 0,\n" +
//        "  \"conventionalKwhConsumption\": 0,\n" +
//        "  \"isFanOven\": true,\n" +
//        "  \"convectionKwhConsumption\": 0,\n" +
//        "  \"conventionalMjConsumption\": 0,\n" +
//        "  \"convectionMjConsumption\": 0\n" +
//        "}";
//
//    mockMvc.perform(post("/api/v1/domestic-ovens/gas-ovens/energy-label")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(apiContent)
//            .accept(MediaType.APPLICATION_JSON)
//            .header("X-Forwarded-For", "1.1.1.1"))
//        .andExpect(status().isOk())
//        .andExpect(header().longValue("X-Rate-Limit-Remaining", 2));
  }

  @TestConfiguration
  @EnableCaching
  static class TestConfig {

  }
}
