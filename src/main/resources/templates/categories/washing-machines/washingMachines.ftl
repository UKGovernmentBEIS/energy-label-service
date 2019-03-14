<#include '../../layout.ftl'>

<@common.standardProductForm "Washing machines">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.annualWaterConsumption"/>
  <@govukTextInput.textInput path="form.capacity"/>
  <@govukSelect.select path="form.spinDryingEfficiencyRating" options=spinDryingEfficiencyRating/>
  <@govukTextInput.textInput path="form.washingNoiseEmissions"/>
  <@govukTextInput.textInput path="form.spinningNoiseEmissions"/>
</@common.standardProductForm>
