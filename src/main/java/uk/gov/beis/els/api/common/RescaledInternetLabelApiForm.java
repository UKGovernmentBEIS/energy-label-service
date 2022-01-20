package uk.gov.beis.els.api.common;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabelColour;

public class RescaledInternetLabelApiForm extends BaseInternetLabelApiForm {

  @ApiModelProperty(value = "Should the arrow be in colour or black and white?", notes = "Use a colour arrow if you can. You can use a black and white arrow if your material is being printed in black and white. You shouldn't use black and white arrows on the internet.")
  @NotNull
  private InternetLabelColour labelColour;
}
