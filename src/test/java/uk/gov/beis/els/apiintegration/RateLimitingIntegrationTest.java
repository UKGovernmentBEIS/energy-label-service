package uk.gov.beis.els.apiintegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.beis.els.service.Bucket4JRequestService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("bucket4j-integration-test")
@AutoConfigureMockMvc
public class RateLimitingIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  protected WebApplicationContext context;

  @Autowired
  protected Bucket4JRequestService bucket4JRequestService;

  @Test
  public void withSingleIpAddress() throws Exception {

    String apiContent = "{\n" +
        "  \"supplierName\": \"string\",\n" +
        "  \"modelName\": \"string\",\n" +
        "  \"efficiencyRating\": \"APPP\",\n" +
        "  \"volume\": 0,\n" +
        "  \"conventionalKwhConsumption\": 0,\n" +
        "  \"isFanOven\": true,\n" +
        "  \"convectionKwhConsumption\": 0,\n" +
        "  \"conventionalMjConsumption\": 0,\n" +
        "  \"convectionMjConsumption\": 0\n" +
        "}";

    mockMvc.perform(post("/api/v1/domestic-ovens/gas-ovens/energy-label")
            .contentType(MediaType.APPLICATION_JSON)
            .content(apiContent)
            .accept(MediaType.APPLICATION_JSON)
            .header("X-Forwarded-For", "1.1.1.1"))
        .andExpect(status().isOk())
        .andExpect(header().longValue("X-Rate-Limit-Remaining", 2));
  }

}
