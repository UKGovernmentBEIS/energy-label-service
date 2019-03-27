package uk.co.fivium.els.model;

import javax.validation.constraints.NotBlank;

public class ukOrEuForm {
  @NotBlank(message = "Select where the item is being sold")
  private String ukOrEuAnswer;

  public String getUkOrEuAnswer() {
    return ukOrEuAnswer;
  }

  public void setUkOrEuAnswer(String ukOrEuAnswer) {
    this.ukOrEuAnswer = ukOrEuAnswer;
  }
}
