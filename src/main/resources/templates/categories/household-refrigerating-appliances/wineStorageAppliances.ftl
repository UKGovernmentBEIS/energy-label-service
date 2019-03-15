<#include '../../layout.ftl'>

<@common.standardProductForm "Wine storage appliances">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.bottleCapacity"/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
