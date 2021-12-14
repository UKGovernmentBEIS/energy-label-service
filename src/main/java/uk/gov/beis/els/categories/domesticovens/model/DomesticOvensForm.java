package uk.gov.beis.els.categories.domesticovens.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.StandardTemplateForm40Char;
import uk.gov.beis.els.categories.domesticovens.service.DomesticOvensService;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@ApiModel(value = "Electric oven energy label")
@GroupSequenceProvider(DomesticOvensFormSequenceProvider.class)
public class DomesticOvensForm extends StandardTemplateForm40Char {

  @FieldPrompt("The energy efficiency class of the cavity")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  @ApiValuesFromLegislationCategory(serviceClass = DomesticOvensService.class)
  private String efficiencyRating;

  @FieldPrompt("Usable volume of the cavity in litres (L)")
  @Digits(integer = 3, fraction = 0, message = "Enter the volume, up to 3 digits long")
  @ApiModelProperty(dataType = "java.lang.Integer")
  @NotNull
  private String volume;

  @FieldPrompt("Energy consumption of the conventional heating function per cycle, in kWh/cycle")
  @Digits(integer = 1, fraction = 2, message = "Enter the energy consumption of the conventional heating function per cycle as 1 digit with up to 2 decimal places")
  @ApiModelProperty(dataType = "java.lang.Float")
  @NotNull
  private String conventionalKwhConsumption;

  @FieldPrompt("Is this a fan-forced oven?")
  @NotNull(message = "Specify if this is a fan-forced oven")
  private Boolean isFanOven;

  @FieldPrompt("Energy consumption of the fan-forced heating function per cycle, in kWh/cycle")
  @Digits(groups = FanOvenGroup.class, integer = 1, fraction = 2, message = "Enter the energy consumption of the fan-forced heating function per cycle as 1 digit with up to 2 decimal places")
  @ApiModelProperty(dataType = "java.lang.Float")
  @NotNull(groups = FanOvenGroup.class)
  private String convectionKwhConsumption;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getConventionalKwhConsumption() {
    return conventionalKwhConsumption;
  }

  public void setConventionalKwhConsumption(String conventionalKwhConsumption) {
    this.conventionalKwhConsumption = conventionalKwhConsumption;
  }

  public Boolean getIsFanOven() {
    return isFanOven;
  }

  public void setIsFanOven(Boolean fanOven) {
    isFanOven = fanOven;
  }

  public String getConvectionKwhConsumption() {
    return convectionKwhConsumption;
  }

  public void setConvectionKwhConsumption(String convectionKwhConsumption) {
    this.convectionKwhConsumption = convectionKwhConsumption;
  }
}
