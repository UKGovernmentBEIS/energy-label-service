package uk.gov.beis.els.categories.refrigeratingappliances.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.categories.common.PostMarch2021Field;
import uk.gov.beis.els.categories.common.PreMarch2021Field;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@GroupSequenceProvider(FridgesFreezersFormSequenceProvider.class)
public class FridgesFreezersForm extends StandardTemplateForm30Char {

  @FieldPrompt("What style of label do you need to create?")
  @NotBlank(message = "Select the style of label you need to create", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String applicableLegislation;

  @FieldPrompt("Energy efficiency class")
  @NotBlank(message = "Select an energy efficiency class", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("Annual energy consumption (AEC) in kWh per year")
  @Digits(integer = 3, fraction = 0, message = "Enter the annual energy consumption, up to 3 digits long")
  private String annualEnergyConsumption;

  @FieldPrompt("Airborne acoustical noise emissions expressed in dB(A) re 1 pW")
  @Digits(integer = 2, fraction = 0, message = "Enter the noise emissions, up to 2 digits long")
  private String noiseEmissions;

  // Pre march 2021 fields
  @FieldPrompt(value = "Does the model have any fridge compartments?", hintText = "A fridge compartment has no star rating and a temperature above minus 6 degrees Celsius")
  @NotNull(message = "Specify if the model has any fridge compartments", groups = PreMarch2021Field.class)
  private Boolean nonRatedCompartmentPreMarch2021;

  @FieldPrompt("Total storage volume of fridge compartments in litres (l)")
  @Digits(groups = FridgeGroupPreMarch2021.class, integer = 3, fraction = 0, message = "Enter the total storage volume of fridge compartments in litres up to 3 digits long")
  private String nonRatedVolumePreMarch2021;

  @FieldPrompt(value = "Does the model have any freezer compartments?", hintText = "A freezer compartment has a star rating between 1 and 4, and a temperature of minus 6 degrees Celsius or below")
  @NotNull(message = "Specify if the model has any freezer compartments", groups = PreMarch2021Field.class)
  private Boolean ratedCompartmentPreMarch2021;

  @FieldPrompt("Total storage volume of freezer compartments in litres (l)")
  @Digits(groups = FreezerGroupPreMarch2021.class, integer = 3, fraction = 0, message = "Enter the total volume of freezer compartments in litres, up to 3 digits long")
  private String ratedVolumePreMarch2021;

  @FieldPrompt("Star rating of the largest freezer compartment")
  @NotBlank(groups = FreezerGroupPreMarch2021.class, message = "Select a star rating")
  private String starRatingPreMarch2021;

  // Post march 2021 fields
  @FieldPrompt(value = "Does the model have any chill or unfrozen compartments?", hintText = "A chill compartment has a target temperature of 2 degrees Celsius and storage conditions between minus 3 degrees Celsius and 3 degrees Celsius. An unfrozen compartment has a target temperature of 4 degrees Celsius or above.")
  @NotNull(message = "Specify if the model has any chill or unfrozen compartments", groups = PostMarch2021Field.class)
  private Boolean nonRatedCompartmentPostMarch2021;

  @FieldPrompt("Total volume of chill and unfrozen compartments in litres (l)")
  @Digits(groups = FridgeGroupPostMarch2021.class, integer = 3, fraction = 0, message = "Enter the total volume of chill and unfrozen compartments in litres up to 3 digits long")
  private String nonRatedVolumePostMarch2021;

  @FieldPrompt(value = "Does the model have any frozen compartments?", hintText = "A frozen compartment has a target temperature of 0 degrees Celsius or below")
  @NotNull(message = "Specify if the model has any frozen compartments", groups = PostMarch2021Field.class)
  private Boolean ratedCompartmentPostMarch2021;

  @FieldPrompt("Total volume of frozen compartments in litres (l)")
  @Digits(groups = FreezerGroupPostMarch2021.class, integer = 3, fraction = 0, message = "Enter the total volume of frozen compartments in litres, up to 3 digits long")
  private String ratedVolumePostMarch2021;

  @FieldPrompt("Airborne acoustic noise emission class")
  @NotBlank(message = "Select an airborne acoustic noise emission class", groups = PostMarch2021Field.class)
  private String noiseEmissionsClass;

  public String getApplicableLegislation() {
    return applicableLegislation;
  }

  public void setApplicableLegislation(String applicableLegislation) {
    this.applicableLegislation = applicableLegislation;
  }

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

  public String getNoiseEmissions() {
    return noiseEmissions;
  }

  public void setNoiseEmissions(String noiseEmissions) {
    this.noiseEmissions = noiseEmissions;
  }

  public Boolean getNonRatedCompartmentPreMarch2021() {
    return nonRatedCompartmentPreMarch2021;
  }

  public void setNonRatedCompartmentPreMarch2021(Boolean nonRatedCompartmentPreMarch2021) {
    this.nonRatedCompartmentPreMarch2021 = nonRatedCompartmentPreMarch2021;
  }

  public String getNonRatedVolumePreMarch2021() {
    return nonRatedVolumePreMarch2021;
  }

  public void setNonRatedVolumePreMarch2021(String nonRatedVolumePreMarch2021) {
    this.nonRatedVolumePreMarch2021 = nonRatedVolumePreMarch2021;
  }

  public Boolean getRatedCompartmentPreMarch2021() {
    return ratedCompartmentPreMarch2021;
  }

  public void setRatedCompartmentPreMarch2021(Boolean ratedCompartmentPreMarch2021) {
    this.ratedCompartmentPreMarch2021 = ratedCompartmentPreMarch2021;
  }

  public String getRatedVolumePreMarch2021() {
    return ratedVolumePreMarch2021;
  }

  public void setRatedVolumePreMarch2021(String ratedVolumePreMarch2021) {
    this.ratedVolumePreMarch2021 = ratedVolumePreMarch2021;
  }

  public String getStarRatingPreMarch2021() {
    return starRatingPreMarch2021;
  }

  public void setStarRatingPreMarch2021(String starRatingPreMarch2021) {
    this.starRatingPreMarch2021 = starRatingPreMarch2021;
  }

  public Boolean getNonRatedCompartmentPostMarch2021() {
    return nonRatedCompartmentPostMarch2021;
  }

  public void setNonRatedCompartmentPostMarch2021(Boolean nonRatedCompartmentPostMarch2021) {
    this.nonRatedCompartmentPostMarch2021 = nonRatedCompartmentPostMarch2021;
  }

  public String getNonRatedVolumePostMarch2021() {
    return nonRatedVolumePostMarch2021;
  }

  public void setNonRatedVolumePostMarch2021(String nonRatedVolumePostMarch2021) {
    this.nonRatedVolumePostMarch2021 = nonRatedVolumePostMarch2021;
  }

  public Boolean getRatedCompartmentPostMarch2021() {
    return ratedCompartmentPostMarch2021;
  }

  public void setRatedCompartmentPostMarch2021(Boolean ratedCompartmentPostMarch2021) {
    this.ratedCompartmentPostMarch2021 = ratedCompartmentPostMarch2021;
  }

  public String getRatedVolumePostMarch2021() {
    return ratedVolumePostMarch2021;
  }

  public void setRatedVolumePostMarch2021(String ratedVolumePostMarch2021) {
    this.ratedVolumePostMarch2021 = ratedVolumePostMarch2021;
  }

  public String getNoiseEmissionsClass() {
    return noiseEmissionsClass;
  }

  public void setNoiseEmissionsClass(String noiseEmissionsClass) {
    this.noiseEmissionsClass = noiseEmissionsClass;
  }
}
