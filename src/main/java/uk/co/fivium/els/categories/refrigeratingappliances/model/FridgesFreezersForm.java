package uk.co.fivium.els.categories.refrigeratingappliances.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

@GroupSequenceProvider(RefrigeratingAppliancesFormSequenceProvider.class)
public class FridgesFreezersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Energy efficiency class")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Annual energy consumption (AEC) in kWh per year")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt(value = "Does the model have any fridge compartments?", hintText = "A fridge compartment has no star rating and a temperature above minus 6 degrees centigrade")
  @NotNull(message = "Specify if the model has any fridge compartments")
  private Boolean nonRatedCompartment;

  @FieldPrompt("Total storage volume of fridge compartments in litres (l)")
  @Digits(groups = FridgeGroup.class, integer = 3, fraction = 0, message = "Enter the total storage volume of fridge compartments in litres up to 3 digits long")
  private String nonRatedVolume;

  @FieldPrompt(value = "Does the model have any freezer compartments?", hintText = "A freezer compartment has a star rating between 1 and 4, and a temperature of minus 6 degrees centigrade or below")
  @NotNull(message = "Specify if the model has any freezer compartments")
  private Boolean ratedCompartment;

  @FieldPrompt("Total storage volume of freezer compartments in litres (l)")
  @Digits(groups = FreezerGroup.class, integer = 3, fraction = 0, message = "Enter the total volume of freezer compartments in litres, up to 3 digits long")
  private String ratedVolume;

  @FieldPrompt("Star rating of the compartments")
  @NotBlank(groups = FreezerGroup.class, message = "Select a star rating")
  private String starRating;

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emissions, up to 2 digits long")
  private String noiseEmissions;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getAnnualEnergyConsumption() {
    return annualEnergyConsumption;
  }

  public void setAnnualEnergyConsumption(String annualEnergyConsumption) {
    this.annualEnergyConsumption = annualEnergyConsumption;
  }

  public String getNonRatedVolume() {
    return nonRatedVolume;
  }

  public void setNonRatedVolume(String nonRatedVolume) {
    this.nonRatedVolume = nonRatedVolume;
  }

  public String getRatedVolume() {
    return ratedVolume;
  }

  public void setRatedVolume(String ratedVolume) {
    this.ratedVolume = ratedVolume;
  }

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }

  public Boolean getNonRatedCompartment() {
    return nonRatedCompartment;
  }

  public void setNonRatedCompartment(Boolean nonRatedCompartment) {
    this.nonRatedCompartment = nonRatedCompartment;
  }

  public Boolean getRatedCompartment() {
    return ratedCompartment;
  }

  public void setRatedCompartment(Boolean ratedCompartment) {
    this.ratedCompartment = ratedCompartment;
  }

  public String getStarRating() {
    return starRating;
  }

  public void setStarRating(String starRating) {
    this.starRating = starRating;
  }
}
