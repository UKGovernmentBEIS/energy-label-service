package uk.co.fivium.els.categories.waterheaters.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import uk.co.fivium.els.categories.common.StandardTemplateForm50Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class WaterSolarPackagesForm extends StandardTemplateForm50Char {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("Water heating energy efficiency class of the water heater")
  @NotBlank(message = "Select an energy efficiency indicator for the water heater")
  private String heaterEfficiencyRating;

  @FieldPrompt("Water heating energy efficiency class of the package of water heater and solar device")
  @NotBlank(message = "Select an energy efficiency indicator for the package of water heater and solar device", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String packageEfficiencyRating;

  @FieldPrompt("May a solar collector be included in the package of water heater and solar device?")
  @NotNull(message = "Specify if a solar collector may be included")
  private Boolean solarCollector;

  @FieldPrompt("May a hot water storage tank be included in the package of water heater and solar device?")
  @NotNull(message = "Specify if a hot water storage tank may be included")
  private Boolean storageTank;

  public String getDeclaredLoadProfile() {
    return declaredLoadProfile;
  }

  public void setDeclaredLoadProfile(String declaredLoadProfile) {
    this.declaredLoadProfile = declaredLoadProfile;
  }

  public String getHeaterEfficiencyRating() {
    return heaterEfficiencyRating;
  }

  public void setHeaterEfficiencyRating(String heaterEfficiencyRating) {
    this.heaterEfficiencyRating = heaterEfficiencyRating;
  }

  public String getPackageEfficiencyRating() {
    return packageEfficiencyRating;
  }

  public void setPackageEfficiencyRating(String packageEfficiencyRating) {
    this.packageEfficiencyRating = packageEfficiencyRating;
  }

  public Boolean getSolarCollector() {
    return solarCollector;
  }

  public void setSolarCollector(Boolean solarCollector) {
    this.solarCollector = solarCollector;
  }

  public Boolean getStorageTank() {
    return storageTank;
  }

  public void setStorageTank(Boolean storageTank) {
    this.storageTank = storageTank;
  }
}
