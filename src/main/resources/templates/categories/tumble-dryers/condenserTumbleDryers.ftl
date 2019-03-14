<#include '../../layout.ftl'>

<@common.standardProductForm "Condenser tumble dryers">

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

  <@govukTextInput.textInput path="form.energyConsumption"/>

  <@govukTextInput.textInput path="form.cycleTime"/>

  <@govukTextInput.textInput path="form.ratedCapacity"/>

  <@govukTextInput.textInput path="form.soundPowerLevel"/>

  <@govukSelect.select path="form.condensationEfficiencyRating" options=condensationEfficiencyRating/>

</@common.standardProductForm>