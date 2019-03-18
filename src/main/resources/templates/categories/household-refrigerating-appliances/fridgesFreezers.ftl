<#include '../../layout.ftl'>

<@common.standardProductForm "Household fridges and freezers">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukRadios.radioYesNo path="form.nonRatedCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.nonRatedVolume"/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.ratedCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.ratedVolume"/>
    <@govukSelect.select path="form.starRating" options=starRating/>
  </@govukRadios.radioYesNo>

  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
