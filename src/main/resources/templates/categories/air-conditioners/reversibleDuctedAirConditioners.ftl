<#include '../../layout.ftl'>

<@common.standardProductForm "Reversible single or double duct air conditioners">

  <@govukSelect.select path="form.coolingEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.coolingKw"/>
  <@govukTextInput.textInput path="form.eerRated"/>
  <@govukTextInput.textInput path="form.coolingHourlyEnergyConsumption"/>

  <@govukSelect.select path="form.heatingEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.heatingKw"/>
  <@govukTextInput.textInput path="form.copRated"/>
  <@govukTextInput.textInput path="form.heatingHourlyEnergyConsumption"/>

  <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

</@common.standardProductForm>