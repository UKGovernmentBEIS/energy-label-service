<#include '../../layout.ftl'>

<@common.standardProductForm "Household refrigerating appliances">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.nonRatedVolume"/>
  <@govukTextInput.textInput path="form.ratedVolume"/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
