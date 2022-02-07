package uk.gov.beis.els.api.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;

public class RescaledInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "Use a colour arrow if you can. You can use a black and white arrow if your material is being printed in black and white. You shouldn't use black and white arrows on the internet.")
  @NotNull
  private InternetLabelColour labelColour;

  public InternetLabelColour getLabelColour() {
    return labelColour;
  }

  public void setLabelColour(InternetLabelColour labelColour) {
    this.labelColour = labelColour;
  }
}
