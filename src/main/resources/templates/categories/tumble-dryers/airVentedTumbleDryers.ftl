<#include '../../layout.ftl'>

<@common.standardProductForm "Air vented tumble dryers">

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

  <@govukTextInput.textInput path="form.energyConsumption"/>

  <@govukTextInput.textInput path="form.cycleTime"/>

  <@govukTextInput.textInput path="form.ratedCapacity"/>

  <@govukTextInput.textInput path="form.soundPowerLevel"/>

</@common.standardProductForm>