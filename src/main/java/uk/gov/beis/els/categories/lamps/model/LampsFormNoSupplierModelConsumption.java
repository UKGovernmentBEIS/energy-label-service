package uk.gov.beis.els.categories.lamps.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.lamps.service.LampsService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Lamps energy label with energy rating only")
@StaticProductText("The label should be at least 36mm x 62mm when attached to packaging. If it doesn’t fit, you can reduce the height by up to 60 percent. It can be full colour or black and white.")
public class LampsFormNoSupplierModelConsumption extends InternetLabellingForm {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(
      serviceClass = LampsService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_PRE_SEPTEMBER_2021"
  )
  private String efficiencyRating;

  @FieldPrompt("Should the label be in colour or black and white?")
  @NotBlank(message = "Select whether the label should be in colour or black and white")
  @ApiValuesFromEnum(TemplateColour.class)
  private String templateColour;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getTemplateColour() {
    return templateColour;
  }

  public void setTemplateColour(String templateColour) {
    this.templateColour = templateColour;
  }
}
