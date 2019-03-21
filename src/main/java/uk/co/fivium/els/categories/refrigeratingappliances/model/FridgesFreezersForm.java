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

  @FieldPrompt("Does this model have at least one refrigerator compartment?")
  @NotNull
  private Boolean nonRatedCompartment;

  @FieldPrompt("Sum of the storage volumes of all compartments that do not merit a star rating (i.e. operating temperature over minus 6°C) in litres")
  @Digits(groups = FridgeGroup.class, integer = 3, fraction = 0, message = "Enter the total volume of compartments that do not merit a star rating, up to 3 digits long")
  private String nonRatedVolume;

  @FieldPrompt("Does this model have at least one freezer compartment?")
  @NotNull
  private Boolean ratedCompartment;

  @FieldPrompt("Sum of the storage volumes of all frozen-food storage compartments that merit a star rating (i.e. operating temperature less than or equal to minus 6°C) in litres")
  @Digits(groups = FreezerGroup.class, integer = 3, fraction = 0, message = "Enter the total volume of compartments that merit a star rating, up to 3 digits long")
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
