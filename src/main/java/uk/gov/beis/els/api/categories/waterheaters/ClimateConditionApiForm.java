package uk.gov.beis.els.api.categories.waterheaters;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitBoth;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitGj;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitKw;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class ClimateConditionApiForm extends StandardTemplateForm30Char {

  @FieldPrompt("How do you want to provide annual energy consumption?")
  @NotBlank(message = "Specify how you want to provide annual energy consumption")
  @ApiValuesFromEnum(EnergyConsumptionUnit.class)
  private String consumptionUnit;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for colder climate conditions, up to 4 digits long", groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
  @NotNull(groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
  @Schema(type = "integer", description = "Annual electricity consumption in kWh/annum for colder climate conditions. Only required if <code>consumptionUnit</code> is <code>KWH</code> or <code>BOTH</code>.")
  private String colderKwhAnnum;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for average climate conditions, up to 4 digits long", groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
  @NotNull(groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
  @Schema(type = "integer", description = "Annual electricity consumption in kWh/annum for average climate conditions. Only required if <code>consumptionUnit</code> is <code>KWH</code> or <code>BOTH</code>.")
  private String averageKwhAnnum;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for warmer climate conditions, up to 4 digits long", groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
  @NotNull(groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
  @Schema(type = "integer", description = "Annual electricity consumption in kWh/annum for warmer climate conditions. Only required if <code>consumptionUnit</code> is <code>KWH</code> or <code>BOTH</code>.")
  private String warmerKwhAnnum;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for colder climate conditions, up to 2 digits long", groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
  @NotNull(groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
  @Schema(type = "integer", description = "Annual fuel consumption in GJ/annum for colder climate conditions. Only required if <code>consumptionUnit</code> is <code>GJ</code> or <code>BOTH</code>.")
  private String colderGjAnnum;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for average climate conditions, up to 2 digits long", groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
  @NotNull(groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
  @Schema(type = "integer", description = "Annual fuel consumption in GJ/annum for average climate conditions. Only required if <code>consumptionUnit</code> is <code>GJ</code> or <code>BOTH</code>.")
  private String averageGjAnnum;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for warmer climate conditions, up to 2 digits long", groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
  @NotNull(groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
  @Schema(type = "integer", description = "Annual fuel consumption in GJ/annum for warmer climate conditions. Only required if <code>consumptionUnit</code> is <code>GJ</code> or <code>BOTH</code>.")
  private String warmerGjAnnum;

  public String getConsumptionUnit() {
    return consumptionUnit;
  }

  public void setConsumptionUnit(String consumptionUnit) {
    this.consumptionUnit = consumptionUnit;
  }

  public String getColderKwhAnnum() {
    return colderKwhAnnum;
  }

  public void setColderKwhAnnum(String colderKwhAnnum) {
    this.colderKwhAnnum = colderKwhAnnum;
  }

  public String getAverageKwhAnnum() {
    return averageKwhAnnum;
  }

  public void setAverageKwhAnnum(String averageKwhAnnum) {
    this.averageKwhAnnum = averageKwhAnnum;
  }

  public String getWarmerKwhAnnum() {
    return warmerKwhAnnum;
  }

  public void setWarmerKwhAnnum(String warmerKwhAnnum) {
    this.warmerKwhAnnum = warmerKwhAnnum;
  }

  public String getColderGjAnnum() {
    return colderGjAnnum;
  }

  public void setColderGjAnnum(String colderGjAnnum) {
    this.colderGjAnnum = colderGjAnnum;
  }

  public String getAverageGjAnnum() {
    return averageGjAnnum;
  }

  public void setAverageGjAnnum(String averageGjAnnum) {
    this.averageGjAnnum = averageGjAnnum;
  }

  public String getWarmerGjAnnum() {
    return warmerGjAnnum;
  }

  public void setWarmerGjAnnum(String warmerGjAnnum) {
    this.warmerGjAnnum = warmerGjAnnum;
  }
}
