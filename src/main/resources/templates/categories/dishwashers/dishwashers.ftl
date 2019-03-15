<#include '../../layout.ftl'>

<@common.standardProductForm "Dishwashers">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.annualWaterConsumption"/>
  <@govukSelect.select path="form.dryingEfficiencyRating" options=dryingEfficiencyRating/>
    <@govukTextInput.textInput path="form.capacity"/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
