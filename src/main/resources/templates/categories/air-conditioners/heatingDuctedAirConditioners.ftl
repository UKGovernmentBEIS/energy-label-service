<#include '../../layout.ftl'>

<@common.standardProductForm "Heating-only single- or double-duct air conditioners">

  <@govukSelect.select path="form.heatingEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.heatingKw"/>
  <@govukTextInput.textInput path="form.copRated"/>
  <@govukTextInput.textInput path="form.heatingHourlyEnergyConsumption"/>

  <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

</@common.standardProductForm>