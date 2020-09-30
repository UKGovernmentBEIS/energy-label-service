<#include '../../layout.ftl'>

<@common.standardProductForm "Professional refrigerated storage cabinets">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukRadios.radioYesNo path="form.chilledCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.chilledVolume"/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.frozenCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.frozenVolume"/>
  </@govukRadios.radioYesNo>

  <@govukSelect.select path="form.climateClass" options=climateClass/>

</@common.standardProductForm>
