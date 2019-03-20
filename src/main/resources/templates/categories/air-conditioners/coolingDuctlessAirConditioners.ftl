<#include '../../layout.ftl'>

<@common.standardProductForm "Cooling-only ductless air conditioners">

  <@govukSelect.select path="form.coolingEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.coolingModeDesignLoad"/>
  <@govukTextInput.textInput path="form.coolingModeSeer"/>
  <@govukTextInput.textInput path="form.coolingAnnualEnergyConsumption"/>

  <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>
  <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

</@common.standardProductForm>