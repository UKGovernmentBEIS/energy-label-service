package uk.gov.beis.els.api.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;

public class RescaledInternetLabelApiForm extends BaseInternetLabelApiForm {

  @Schema(description = "The colour of the arrow image. Use a colour arrow if you can. You can use a black and white arrow if your material is being printed in black and white. You shouldn't use black and white arrows on the internet.")
  @NotNull
  @ApiValuesFromEnum(value = InternetLabelColour.class)
  private String labelColour;

  public String getLabelColour() {
    return labelColour;
  }

  public void setLabelColour(String labelColour) {
    this.labelColour = labelColour;
  }
}
