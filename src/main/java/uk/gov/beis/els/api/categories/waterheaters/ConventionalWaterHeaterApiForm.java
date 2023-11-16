package uk.gov.beis.els.api.categories.waterheaters;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import org.hibernate.validator.group.GroupSequenceProvider;
import uk.gov.beis.els.api.common.ApiValuesFromEnum;
import uk.gov.beis.els.api.common.ApiValuesFromLegislationCategory;
import uk.gov.beis.els.categories.common.LoadProfile;
import uk.gov.beis.els.categories.common.StandardTemplateForm30Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.categories.waterheaters.model.EnergyConsumptionUnit;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitBoth;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitGj;
import uk.gov.beis.els.categories.waterheaters.model.validation.ConsumptionUnitKw;
import uk.gov.beis.els.categories.waterheaters.service.WaterHeatersService;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;

@Schema(name = "Conventional water heater energy label")
@GroupSequenceProvider(ConventionalWaterHeaterApiFormSequenceProvider.class)
public class ConventionalWaterHeaterApiForm extends StandardTemplateForm30Char {

    @FieldPrompt("Declared load profile")
    @NotBlank(message = "Select a declared load profile")
    @ApiValuesFromEnum(value = LoadProfile.class)
    private String declaredLoadProfile;

    @FieldPrompt("Water heating energy efficiency class")
    @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
    @DualModeField
    @ApiValuesFromLegislationCategory(serviceClass = WaterHeatersService.class)
    private String efficiencyRating;

    @FieldPrompt("How do you want to provide annual energy consumption?")
    @NotBlank(message = "Specify how you want to provide annual energy consumption")
    @ApiValuesFromEnum(EnergyConsumptionUnit.class)
    private String consumptionUnit;

    @FieldPrompt("Annual electricity consumption in kWh/annum")
    @Digits(integer = 4, fraction = 0, message = "Enter the annual electricity consumption, up to 4 digits long", groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
    @NotNull(groups = {ConsumptionUnitKw.class, ConsumptionUnitBoth.class})
    @Schema(type = "integer", description = "Annual electricity consumption in kWh/annum. Only required if <code>consumptionUnit</code> is <code>KWH</code> or <code>BOTH</code>.")
    private String kwhAnnum;

    @FieldPrompt("Annual fuel consumption in GJ/annum")
    @Digits(integer = 2, fraction = 0, message = "Enter the annual fuel consumption, up to 2 digits long", groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
    @NotNull(groups = {ConsumptionUnitGj.class, ConsumptionUnitBoth.class})
    @Schema(type = "integer", description = "Annual fuel consumption in GJ/annum. Only required if <code>consumptionUnit</code> is <code>GJ</code> or <code>BOTH</code>.")
    private String gjAnnum;

    @FieldPrompt("Sound power level, indoors dB")
    @Digits(integer = 2, fraction = 0, message = "Enter the indoors sound power level, up to 2 digits long")
    @NotNull
    @Schema(type = "integer")
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
