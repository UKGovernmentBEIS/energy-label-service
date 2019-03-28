<#include '../../layout.ftl'>

<@common.standardProductForm "Cooling-only single- or double-duct air conditioners">

  <@govukSelect.select path="form.coolingEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.coolingKw"/>
  <@govukTextInput.textInput path="form.eerRated"/>
  <@govukTextInput.textInput path="form.coolingHourlyEnergyConsumption"/>

  <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

</@common.standardProductForm>