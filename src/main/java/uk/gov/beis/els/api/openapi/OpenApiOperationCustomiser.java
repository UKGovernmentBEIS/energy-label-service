package uk.gov.beis.els.api.openapi;

import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class OpenApiOperationCustomiser implements OperationCustomizer {

  private static final String API_CALCULATOR_DISCLAIMER_TEXT = "You're responsible for making sure the energy label and fiche are calculated accurately. You must check the label and fiche carefully before you provide them to your customers.";

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    addCalculatorDisclaimerText(operation);
    return operation;
  }

  private void addCalculatorDisclaimerText(Operation operation) {
    if (operation.getSummary() == null) {
      return;
    }

    String operationSummary = operation.getSummary().toLowerCase();
    if (operationSummary.contains("calculator") || operationSummary.contains("fiche")) {
      String existingDescription = operation.getDescription();
      if (existingDescription == null) {
        operation.setDescription(API_CALCULATOR_DISCLAIMER_TEXT);
      } else {
        operation.setDescription(existingDescription + ". " + API_CALCULATOR_DISCLAIMER_TEXT);
      }
    }
  }
}
