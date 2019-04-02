package uk.gov.beis.els.categories.waterheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitBoth;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitGj;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitKw;
import uk.gov.beis.els.model.meta.FieldPrompt;

public class ClimateConditionForm extends StandardTemplateForm30Char {

  @FieldPrompt("How do you want to provide annual energy consumption?")
  @NotBlank(message = "Specify how you want to provide annual energy consumption")
  private String consumptionUnit;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for colder climate conditions, up to 4 digits long", groups = ConsumptionUnitKw.class)
  private String colderKwhAnnumSingle;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for average climate conditions, up to 4 digits long", groups = ConsumptionUnitKw.class)
  private String averageKwhAnnumSingle;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for warmer climate conditions, up to 4 digits long", groups = ConsumptionUnitKw.class)
  private String warmerKwhAnnumSingle;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for colder climate conditions, up to 2 digits long", groups = ConsumptionUnitGj.class)
  private String colderGjAnnumSingle;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for average climate conditions, up to 2 digits long", groups = ConsumptionUnitGj.class)
  private String averageGjAnnumSingle;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for warmer climate conditions, up to 2 digits long", groups = ConsumptionUnitGj.class)
  private String warmerGjAnnumSingle;


  // Dual unit fields must be separate to avoid them being treated as lists.
  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for colder climate conditions, up to 4 digits long", groups = ConsumptionUnitBoth.class)
  private String colderKwhAnnumBoth;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for average climate conditions, up to 4 digits long", groups = ConsumptionUnitBoth.class)
  private String averageKwhAnnumBoth;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption for warmer climate conditions, up to 4 digits long", groups = ConsumptionUnitBoth.class)
  private String warmerKwhAnnumBoth;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for colder climate conditions, up to 2 digits long", groups = ConsumptionUnitBoth.class)
  private String colderGjAnnumBoth;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for average climate conditions, up to 2 digits long", groups = ConsumptionUnitBoth.class)
  private String averageGjAnnumBoth;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption for warmer climate conditions, up to 2 digits long", groups = ConsumptionUnitBoth.class)
  private String warmerGjAnnumBoth;


  public String getConsumptionUnit() {
    return consumptionUnit;
  }

  public void setConsumptionUnit(String consumptionUnit) {
    this.consumptionUnit = consumptionUnit;
  }

  public String getColderKwhAnnumSingle() {
    return colderKwhAnnumSingle;
  }

  public void setColderKwhAnnumSingle(String colderKwhAnnumSingle) {
    this.colderKwhAnnumSingle = colderKwhAnnumSingle;
  }

  public String getAverageKwhAnnumSingle() {
    return averageKwhAnnumSingle;
  }

  public void setAverageKwhAnnumSingle(String averageKwhAnnumSingle) {
    this.averageKwhAnnumSingle = averageKwhAnnumSingle;
  }

  public String getWarmerKwhAnnumSingle() {
    return warmerKwhAnnumSingle;
  }

  public void setWarmerKwhAnnumSingle(String warmerKwhAnnumSingle) {
    this.warmerKwhAnnumSingle = warmerKwhAnnumSingle;
  }

  public String getColderGjAnnumSingle() {
    return colderGjAnnumSingle;
  }

  public void setColderGjAnnumSingle(String colderGjAnnumSingle) {
    this.colderGjAnnumSingle = colderGjAnnumSingle;
  }

  public String getAverageGjAnnumSingle() {
    return averageGjAnnumSingle;
  }

  public void setAverageGjAnnumSingle(String averageGjAnnumSingle) {
    this.averageGjAnnumSingle = averageGjAnnumSingle;
  }

  public String getWarmerGjAnnumSingle() {
    return warmerGjAnnumSingle;
  }

  public void setWarmerGjAnnumSingle(String warmerGjAnnumSingle) {
    this.warmerGjAnnumSingle = warmerGjAnnumSingle;
  }

  public String getColderKwhAnnumBoth() {
    return colderKwhAnnumBoth;
  }

  public void setColderKwhAnnumBoth(String colderKwhAnnumBoth) {
    this.colderKwhAnnumBoth = colderKwhAnnumBoth;
  }

  public String getAverageKwhAnnumBoth() {
    return averageKwhAnnumBoth;
  }

  public void setAverageKwhAnnumBoth(String averageKwhAnnumBoth) {
    this.averageKwhAnnumBoth = averageKwhAnnumBoth;
  }

  public String getWarmerKwhAnnumBoth() {
    return warmerKwhAnnumBoth;
  }

  public void setWarmerKwhAnnumBoth(String warmerKwhAnnumBoth) {
    this.warmerKwhAnnumBoth = warmerKwhAnnumBoth;
  }

  public String getColderGjAnnumBoth() {
    return colderGjAnnumBoth;
  }

  public void setColderGjAnnumBoth(String colderGjAnnumBoth) {
    this.colderGjAnnumBoth = colderGjAnnumBoth;
  }

  public String getAverageGjAnnumBoth() {
    return averageGjAnnumBoth;
  }

  public void setAverageGjAnnumBoth(String averageGjAnnumBoth) {
    this.averageGjAnnumBoth = averageGjAnnumBoth;
  }

  public String getWarmerGjAnnumBoth() {
    return warmerGjAnnumBoth;
  }

  public void setWarmerGjAnnumBoth(String warmerGjAnnumBoth) {
    this.warmerGjAnnumBoth = warmerGjAnnumBoth;
  }
}
