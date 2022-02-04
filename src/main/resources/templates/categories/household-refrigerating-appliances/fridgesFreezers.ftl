<#include '../../layout.ftl'>

<@common.standardProductForm "Household fridges and freezers">
  <@govukTextInput.textInput path="form.qrCodeUrl"/>

  <@govukRadios.radioYesNo path="form.nonRatedCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.nonRatedVolume"/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.ratedCompartment" inline=false hiddenQuestionsWithYesSelected=true>
      <@govukTextInput.textInput path="form.ratedVolume"/>
  </@govukRadios.radioYesNo>

  <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>

  <@govukTextInput.textInput path="form.noiseEmissions"/>

  <@common.rescaledInternetLabellingFields/>
</@common.standardProductForm>
