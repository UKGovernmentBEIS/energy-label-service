package uk.co.fivium.els.categories.airconditioners.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.model.meta.FieldPrompt;

@GroupSequenceProvider(HeatingDuctlessAirConditionersFormSequenceProvider.class)
public class HeatingDuctlessAirConditionersForm extends MultipleClimateGroupForm {

  @FieldPrompt("Energy efficiency class for Average heating season")
  @NotBlank(message = "Select an energy efficiency indicator for average climate conditions")
  private String averageHeatingEfficiencyRating;

  @FieldPrompt("Design load for heating in average climate conditions in kW")
  @Digits(integer = 2, fraction = 1, message = "Enter the design load for average climate conditions, up to 2 digits with an optional decimal place")
  private String averageHeatingDesignLoad;

  @FieldPrompt("Seasonal coefficient of performance in average climate conditions (SCOP value)")
  @Digits(integer = 1, fraction = 1, message = "Enter the SCOP value for average climate conditions, 1 digit with an optional decimal place")
  private String averageScop;

  @FieldPrompt("Annual energy consumption in kWh per year in average climate conditions")
  @Digits(integer = 4, fraction = 0,message = "Enter the annual energy consumption for average climate conditions, up to 4 digits")
  private String averageAnnualEnergyConsumption;

  @FieldPrompt("Sound power levels for indoor units expressed in dB(A) re1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power levels for outdoor units expressed in dB(A) re1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelOutdoors;

  public String getAverageHeatingEfficiencyRating() {
    return averageHeatingEfficiencyRating;
  }

  public void setAverageHeatingEfficiencyRating(String averageHeatingEfficiencyRating) {
    this.averageHeatingEfficiencyRating = averageHeatingEfficiencyRating;
  }

  public String getAverageHeatingDesignLoad() {
    return averageHeatingDesignLoad;
  }

  public void setAverageHeatingDesignLoad(String averageHeatingDesignLoad) {
    this.averageHeatingDesignLoad = averageHeatingDesignLoad;
  }

  public String getAverageScop() {
    return averageScop;
  }

  public void setAverageScop(String averageScop) {
    this.averageScop = averageScop;
  }

  public String getAverageAnnualEnergyConsumption() {
    return averageAnnualEnergyConsumption;
  }

  public void setAverageAnnualEnergyConsumption(String averageAnnualEnergyConsumption) {
    this.averageAnnualEnergyConsumption = averageAnnualEnergyConsumption;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public String getSoundPowerLevelOutdoors() {
    return soundPowerLevelOutdoors;
  }

  public void setSoundPowerLevelOutdoors(String soundPowerLevelOutdoors) {
    this.soundPowerLevelOutdoors = soundPowerLevelOutdoors;
  }
}
