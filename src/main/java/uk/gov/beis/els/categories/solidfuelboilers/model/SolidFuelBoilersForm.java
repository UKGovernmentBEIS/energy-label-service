package uk.gov.beis.els.categories.solidfuelboilers.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.solidfuelboilers.service.SolidFuelBoilersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Solid fuel boiler energy label")
@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
public class SolidFuelBoilersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = SolidFuelBoilersService.class)
  private String efficiencyRating;

  @FieldPrompt("The rated heat output in kW")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output, up to 2 digits long")
  @NotNull
  @Schema(type = "integer")
  private String ratedHeatOutput;

  @FieldPrompt("Is this model a combination boiler?")
  @NotNull(message = "Specify if this model is a combination boiler")
  private Boolean combination;

  @FieldPrompt("Is this model a cogeneration boiler?")
  @NotNull(message = "Specify if this model is a cogeneration boiler")
  private Boolean cogeneration;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getRatedHeatOutput() {
    return ratedHeatOutput;
  }

  public void setRatedHeatOutput(String ratedHeatOutput) {
    this.ratedHeatOutput = ratedHeatOutput;
  }

  public Boolean getCombination() {
    return combination;
  }

  public void setCombination(Boolean combination) {
    this.combination = combination;
  }

  public Boolean getCogeneration() {
    return cogeneration;
  }

  public void setCogeneration(Boolean cogeneration) {
    this.cogeneration = cogeneration;
  }
}
