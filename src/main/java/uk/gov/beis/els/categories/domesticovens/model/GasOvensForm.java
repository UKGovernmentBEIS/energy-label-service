package uk.gov.beis.els.categories.domesticovens.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Gas oven energy label")
@GroupSequenceProvider(GasOvensFormSequenceProvider.class)
public class GasOvensForm extends DomesticOvensForm {

  @FieldPrompt("Energy consumption of the conventional heating function per cycle, in MJ/cycle")
  @Digits(integer = 1, fraction = 2, message = "Enter the energy consumption of the conventional heating function per cycle as 1 digit with up to 2 decimal places")
  @NotNull
  @Schema(type = "number")
  private String conventionalMjConsumption;

  @FieldPrompt("Energy consumption of the fan-forced heating function per cycle, in MJ/cycle")
  @Digits(groups = FanOvenGroup.class, integer = 1, fraction = 2, message = "Enter the energy consumption of the fan-forced heating function per cycle as 1 digit with up to 2 decimal places")
  @NotNull(groups = FanOvenGroup.class)
  @Schema(type = "number", description = "Energy consumption of the fan-forced heating function per cycle, in MJ/cycle. Only required if <code>isFanOven</code> is <code>true</code>.")
  private String convectionMjConsumption;

  public String getConventionalMjConsumption() {
    return conventionalMjConsumption;
  }

  public void setConventionalMjConsumption(String conventionalMjConsumption) {
    this.conventionalMjConsumption = conventionalMjConsumption;
  }

  public String getConvectionMjConsumption() {
    return convectionMjConsumption;
  }

  public void setConvectionMjConsumption(String convectionMjConsumption) {
    this.convectionMjConsumption = convectionMjConsumption;
  }
}
