package uk.co.fivium.elp.categories.airconditioners.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.springframework.format.annotation.NumberFormat;

@GroupSequenceProvider(ReversibleAirConditionersFormSequenceProvider.class)
public class ReversibleAirConditionersForm {

  @NotBlank
  private String supplierName;

  @NotBlank
  private String modelIdentifier;

  @NotBlank
  private String overallEfficiencyClass;

  @NotNull
//  @NumberFormat(pattern = "#.######")
  // positive number, max 1 dp (EU limit to 2 integers)
  @Pattern(regexp = "^(([1-9]*)|(([1-9]*)\\.([0-9])))$") // TODO make a custom validator for number patterns
  private String coolingModeDesignLoad;

  @NotNull
  @NumberFormat(pattern = "#.######")
  private Double coolingModeSeer;

  @NotNull
  @NumberFormat(pattern = "#.######")
  private Integer annualEnergyConsumption;

  @NotBlank
  private String averageEfficiencyClass;

  @NotNull
  @NumberFormat(pattern = "#.######")
  private Double averageHeatingModeDesignLoad;

  @NotNull
  @NumberFormat(pattern = "#.######")
  private Integer averageAnnualEnergyConsumption;

  @NotNull
  @NumberFormat(pattern = "#.######")
  private Double averageScop;


  @NotNull
  private Boolean wamerScopAvailable;
  @NotBlank(groups = WarmerClimateGroup.class)
  private String warmerEfficiencyClass;
  @NotNull(groups = WarmerClimateGroup.class)
  private Double warmerHeatingModeDesignLoad;
  @NotNull(groups = WarmerClimateGroup.class)
  private Integer warmerAnnualEnergyConsumption;
  @NotNull(groups = WarmerClimateGroup.class)
  private Double warmerScop;


  @NotNull
  private Boolean colderScopAvailable;
  @NotBlank(groups = ColderClimateGroup.class)
  private String colderEfficiencyClass;
  @NotNull(groups = ColderClimateGroup.class)
  private Double colderHeatingModeDesignLoad;
  @NotNull(groups = ColderClimateGroup.class)
  private Integer colderAnnualEnergyConsumption;
  @NotNull(groups = ColderClimateGroup.class)
  private Double colderScop;


  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getModelIdentifier() {
    return modelIdentifier;
  }

  public void setModelIdentifier(String modelIdentifier) {
    this.modelIdentifier = modelIdentifier;
  }

  public String getOverallEfficiencyClass() {
    return overallEfficiencyClass;
  }

  public void setOverallEfficiencyClass(String overallEfficiencyClass) {
    this.overallEfficiencyClass = overallEfficiencyClass;
  }

  public String getCoolingModeDesignLoad() {
    return coolingModeDesignLoad;
  }

  public void setCoolingModeDesignLoad(String coolingModeDesignLoad) {
    this.coolingModeDesignLoad = coolingModeDesignLoad;
  }

  public Double getCoolingModeSeer() {
    return coolingModeSeer;
  }

  public void setCoolingModeSeer(Double coolingModeSeer) {
    this.coolingModeSeer = coolingModeSeer;
  }

  public Integer getAnnualEnergyConsumption() {
    return annualEnergyConsumption;
  }

  public void setAnnualEnergyConsumption(Integer annualEnergyConsumption) {
    this.annualEnergyConsumption = annualEnergyConsumption;
  }

  public String getAverageEfficiencyClass() {
    return averageEfficiencyClass;
  }

  public void setAverageEfficiencyClass(String averageEfficiencyClass) {
    this.averageEfficiencyClass = averageEfficiencyClass;
  }

  public Double getAverageHeatingModeDesignLoad() {
    return averageHeatingModeDesignLoad;
  }

  public void setAverageHeatingModeDesignLoad(Double averageHeatingModeDesignLoad) {
    this.averageHeatingModeDesignLoad = averageHeatingModeDesignLoad;
  }

  public Integer getAverageAnnualEnergyConsumption() {
    return averageAnnualEnergyConsumption;
  }

  public void setAverageAnnualEnergyConsumption(Integer averageAnnualEnergyConsumption) {
    this.averageAnnualEnergyConsumption = averageAnnualEnergyConsumption;
  }

  public Double getAverageScop() {
    return averageScop;
  }

  public void setAverageScop(Double averageScop) {
    this.averageScop = averageScop;
  }

  public Boolean getWamerScopAvailable() {
    return wamerScopAvailable;
  }

  public void setWamerScopAvailable(Boolean wamerScopAvailable) {
    this.wamerScopAvailable = wamerScopAvailable;
  }

  public String getWarmerEfficiencyClass() {
    return warmerEfficiencyClass;
  }

  public void setWarmerEfficiencyClass(String warmerEfficiencyClass) {
    this.warmerEfficiencyClass = warmerEfficiencyClass;
  }

  public Double getWarmerHeatingModeDesignLoad() {
    return warmerHeatingModeDesignLoad;
  }

  public void setWarmerHeatingModeDesignLoad(Double warmerHeatingModeDesignLoad) {
    this.warmerHeatingModeDesignLoad = warmerHeatingModeDesignLoad;
  }

  public Integer getWarmerAnnualEnergyConsumption() {
    return warmerAnnualEnergyConsumption;
  }

  public void setWarmerAnnualEnergyConsumption(Integer warmerAnnualEnergyConsumption) {
    this.warmerAnnualEnergyConsumption = warmerAnnualEnergyConsumption;
  }

  public Double getWarmerScop() {
    return warmerScop;
  }

  public void setWarmerScop(Double warmerScop) {
    this.warmerScop = warmerScop;
  }

  public Boolean getColderScopAvailable() {
    return colderScopAvailable;
  }

  public void setColderScopAvailable(Boolean colderScopAvailable) {
    this.colderScopAvailable = colderScopAvailable;
  }

  public String getColderEfficiencyClass() {
    return colderEfficiencyClass;
  }

  public void setColderEfficiencyClass(String colderEfficiencyClass) {
    this.colderEfficiencyClass = colderEfficiencyClass;
  }

  public Double getColderHeatingModeDesignLoad() {
    return colderHeatingModeDesignLoad;
  }

  public void setColderHeatingModeDesignLoad(Double colderHeatingModeDesignLoad) {
    this.colderHeatingModeDesignLoad = colderHeatingModeDesignLoad;
  }

  public Integer getColderAnnualEnergyConsumption() {
    return colderAnnualEnergyConsumption;
  }

  public void setColderAnnualEnergyConsumption(Integer colderAnnualEnergyConsumption) {
    this.colderAnnualEnergyConsumption = colderAnnualEnergyConsumption;
  }

  public Double getColderScop() {
    return colderScop;
  }

  public void setColderScop(Double colderScop) {
    this.colderScop = colderScop;
  }
}
