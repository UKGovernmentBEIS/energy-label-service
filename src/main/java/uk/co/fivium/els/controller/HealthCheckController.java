package uk.co.fivium.els.controller;

import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthCheckController {

  @GetMapping("/health")
  @ResponseBody
  public Map<String, String> healthCheck() {
    return Collections.singletonMap("status", "UP");
  }

}
