package uk.gov.beis.els.apiintegration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
  public void withSingleIpAddress() {
    successfulWebRequest("/api/v1/domestic-ovens/gas-ovens/energy-label", "1.1.1.1", 2);
  }

  @Test
  public void withMultipleIpAddresses() {
    String url = "/api/v1/domestic-ovens/gas-ovens/energy-label";
    successfulWebRequest(url, "1.1.1.1, 2.2.2.2", 2);
    successfulWebRequest(url, "3.3.3.3, 2.2.2.2", 1);
  }

  @Test
  public void withDifferentIpAddresses() {
    String url = "/api/v1/domestic-ovens/gas-ovens/energy-label";
    successfulWebRequest(url, "1.1.1.1, 2.2.2.2", 2);
    successfulWebRequest(url, "2.2.2.2", 1);
    successfulWebRequest(url, "3.3.3.3", 2);
  }

  @Test(expected = RuntimeException.class)
  public void withEmptyXForwardedForHeader() throws Exception {
    mockMvc.perform(post("/api/v1/domestic-ovens/gas-ovens/energy-label")
            .contentType(MediaType.APPLICATION_JSON)
            .content(getApiContent())
            .accept(MediaType.APPLICATION_JSON)
            .header("X-Forwarded-For", ""));
  }

  @Test(expected = RuntimeException.class)
  public void withNoXForwardedForHeader() throws Exception {
    mockMvc.perform(post("/api/v1/domestic-ovens/gas-ovens/energy-label")
        .contentType(MediaType.APPLICATION_JSON)
        .content(getApiContent())
        .accept(MediaType.APPLICATION_JSON));
  }

  @Test
  public void requestLimitTest() {
    String url = "/api/v1/domestic-ovens/gas-ovens/energy-label";
    String xForwardedForHeader = "1.1.1.1";
    IntStream.rangeClosed(1, 3)
        .boxed()
        .sorted(Collections.reverseOrder())
        .forEach(counter -> {
          successfulWebRequest(url, xForwardedForHeader, counter - 1);
        });

    blockedWebRequestDueToRateLimit(url, xForwardedForHeader);
  }

  private void successfulWebRequest(String url, String xForwardedForHeader, Integer remainingTries) {
    try {
      mockMvc.perform(post(url)
              .contentType(MediaType.APPLICATION_JSON)
              .content(getApiContent())
              .accept(MediaType.APPLICATION_JSON)
              .header("X-Forwarded-For", xForwardedForHeader))
          .andExpect(status().isOk())
          .andExpect(header().longValue("X-Rate-Limit-Remaining", remainingTries));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private void blockedWebRequestDueToRateLimit(String url, String xForwardedForHeader) {
    try {
      mockMvc.perform(post(url)
          .contentType(MediaType.APPLICATION_JSON)
          .content(getApiContent())
          .accept(MediaType.APPLICATION_JSON)
          .header("X-Forwarded-For", xForwardedForHeader))
          .andExpect(status().is(HttpStatus.TOO_MANY_REQUESTS.value()))
          .andExpect(header().exists("X-Rate-Limit-Retry-After-Seconds"))
          .andExpect(content().string(containsString("{ \"status\" : 429, \"error\": \"Too many requests\", \"message\" : \"You have exhausted your API request quota\" }")));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

  }

  private String getApiContent() {
    return "{\n" +
        "  \"supplierName\": \"string\",\n" +
        "  \"modelName\": \"string\",\n" +
        "  \"efficiencyRating\": \"APPP\",\n" +
        "  \"volume\": 0,\n" +
        "  \"conventionalKwhConsumption\": 0,\n" +
        "  \"isFanOven\": true,\n" +
        "  \"convectionKwhConsumption\": 0,\n" +
        "  \"conventionalMjConsumption\": 0,\n" +
        "  \"convectionMjConsumption\": 0\n," +
        "  \"outputFormat\": \"PDF\"" +
        "}";
  }

}
