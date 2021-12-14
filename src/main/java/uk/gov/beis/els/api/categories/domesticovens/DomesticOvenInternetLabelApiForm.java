package uk.gov.beis.els.api.categories.domesticovens;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.api.common.BaseInternetLabelApiForm;
import uk.gov.beis.els.categories.domesticovens.service.DomesticOvensService;

@ApiModel("Domestic oven internet label")
public class DomesticOvenInternetLabelApiForm extends BaseInternetLabelApiForm {

  @ApiModelProperty("The energy efficiency class of the cavity")
  @NotBlank
  @ApiValuesFromLegislationCategory(serviceClass = DomesticOvensService.class)
  private String efficiencyRating;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }
}
