package uk.co.fivium.els.categories.waterheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.co.fivium.els.categories.common.StandardTemplateForm30Char;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.co.fivium.els.categories.waterheaters.model.validation.ConsumptionUnitBoth;
import uk.co.fivium.els.categories.waterheaters.model.validation.ConsumptionUnitGj;
import uk.co.fivium.els.categories.waterheaters.model.validation.ConsumptionUnitKw;
import uk.co.fivium.els.categories.waterheaters.model.validation.ConventionalWaterHeaterFormSequenceProvider;
import uk.co.fivium.els.model.meta.DualModeField;
import uk.co.fivium.els.model.meta.FieldPrompt;
import uk.co.fivium.els.model.meta.StaticProductText;

@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
@GroupSequenceProvider(ConventionalWaterHeaterFormSequenceProvider.class)
public class ConventionalWaterHeatersForm extends StandardTemplateForm30Char {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("Water heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String efficiencyRating;

  @FieldPrompt("How do you want to provide annual energy consumption?")
  @NotBlank(message = "Specify how want to provide annual energy consumption")
  private String consumptionUnit;

  @FieldPrompt("Annual electricity consumption in kWh/annum")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption, up to 4 digits long", groups = ConsumptionUnitKw.class)
  private String kwhAnnum;

  @FieldPrompt("Annual fuel consumption in GJ/annum")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption, up to 2 digits long", groups = ConsumptionUnitGj.class)
  private String gjAnnum;

  // These need to be separate otherwise the kw/gj fields above are treated as lists because they appear multiple times in the dom
  @FieldPrompt("Annual electricity consumption in kWh/annum")
  @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption, up to 4 digits long", groups = ConsumptionUnitBoth.class)
  private String bothKwhAnnum;

  @FieldPrompt("Annual fuel consumption in GJ/annum")
  @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption, up to 2 digits long", groups = ConsumptionUnitBoth.class)
  private String bothGjAnnum;

  @FieldPrompt("Sound power level, indoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Can the heater be set to work only during off-peak hours?")
  @NotNull(message = "Specify if off-peak operation is supported")
  private Boolean offPeak;

  public String getDeclaredLoadProfile() {
    return declaredLoadProfile;
  }

  public void setDeclaredLoadProfile(String declaredLoadProfile) {
    this.declaredLoadProfile = declaredLoadProfile;
  }

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getConsumptionUnit() {
    return consumptionUnit;
  }

  public void setConsumptionUnit(String consumptionUnit) {
    this.consumptionUnit = consumptionUnit;
  }

  public String getKwhAnnum() {
    return kwhAnnum;
  }

  public void setKwhAnnum(String kwhAnnum) {
    this.kwhAnnum = kwhAnnum;
  }

  public String getGjAnnum() {
    return gjAnnum;
  }

  public void setGjAnnum(String gjAnnum) {
    this.gjAnnum = gjAnnum;
  }

  public String getBothKwhAnnum() {
    return bothKwhAnnum;
  }

  public void setBothKwhAnnum(String bothKwhAnnum) {
    this.bothKwhAnnum = bothKwhAnnum;
  }

  public String getBothGjAnnum() {
    return bothGjAnnum;
  }

  public void setBothGjAnnum(String bothGjAnnum) {
    this.bothGjAnnum = bothGjAnnum;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public Boolean getOffPeak() {
    return offPeak;
  }

  public void setOffPeak(Boolean offPeak) {
    this.offPeak = offPeak;
  }
}
