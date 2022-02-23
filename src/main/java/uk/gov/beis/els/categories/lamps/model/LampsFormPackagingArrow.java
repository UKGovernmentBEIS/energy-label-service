package uk.gov.beis.els.categories.lamps.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.AnalyticsForm;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.lamps.service.LampsService;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@Schema(name = "Energy rating arrow for light source packaging")
@StaticProductText("The arrow must be shown on the front of the packaging if the energy label isn't on the front. It must be clearly visible and legible. You don't need to include this arrow on the packaging if the energy label is on the front.")
public class LampsFormPackagingArrow extends AnalyticsForm {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @ApiValuesFromLegislationCategory(
      serviceClass = LampsService.class,
      legislationCategoryFieldName = "LEGISLATION_CATEGORY_POST_SEPTEMBER_2021"
  )
  private String efficiencyRating;

  @FieldPrompt(value = "Should the arrow be in colour or black and white?", hintText = "You must only use a black and white arrow if all other information on the packaging, including graphics, is printed in black and white")
  @NotBlank(message = "Select whether the arrow be in colour or black and white")
  @ApiValuesFromEnum(TemplateColour.class)
  private String templateColour;

  @FieldPrompt("Arrow direction")
  @NotBlank(message = "Select an arrow direction")
  @ApiValuesFromEnum(LightSourceArrowOrientation.class)
  private String labelOrientation;

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

  public String getLabelOrientation() {
    return labelOrientation;
  }

  public void setLabelOrientation(String labelOrientation) {
    this.labelOrientation = labelOrientation;
  }
}
