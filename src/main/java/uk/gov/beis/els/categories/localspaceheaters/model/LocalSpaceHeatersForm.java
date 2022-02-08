package uk.gov.beis.els.categories.localspaceheaters.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.localspaceheaters.service.LocalSpaceHeatersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Local space heaters energy label")
@StaticProductText("You must attach the label to the front or top of the product so that it’s easy to see. It must be at least 105mm x 200mm when printed.")
@GroupSequenceProvider(LocalSpaceHeatersFormSequenceProvider.class)
public class LocalSpaceHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("The energy efficiency class of the model")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = LocalSpaceHeatersService.class)
  private String efficiencyRating;

  @FieldPrompt("The direct heat output in kW")
  @Digits(integer = 2, fraction = 1, message = "Enter the direct heat output, up to 2 digits long with an optional decimal place")
  @Schema(type = "number")
  @NotBlank
  private String directHeatOutput;

  @FieldPrompt("Is this model a local space heater with transfer to a fluid?")
  @NotNull(message = "Select whether this model can transfer heat to a fluid")
  private Boolean fluidTransfer;

  @FieldPrompt("The indirect heat output in kW")
  @Digits(groups = HeatTransferGroup.class, integer = 2, fraction = 1, message = "Enter the indirect heat output, up to 2 digits long with an optional decimal place")
  @Schema(type = "number")
  private String indirectHeatOutput;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getDirectHeatOutput() {
    return directHeatOutput;
  }

  public void setDirectHeatOutput(String directHeatOutput) {
    this.directHeatOutput = directHeatOutput;
  }

  public Boolean getFluidTransfer() {
    return fluidTransfer;
  }

  public void setFluidTransfer(Boolean fluidTransfer) {
    this.fluidTransfer = fluidTransfer;
  }

  public String getIndirectHeatOutput() {
    return indirectHeatOutput;
  }

  public void setIndirectHeatOutput(String indirectHeatOutput) {
    this.indirectHeatOutput = indirectHeatOutput;
  }
}
