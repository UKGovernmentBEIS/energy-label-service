<#include '../../layout.ftl'>

<@common.standardProductForm "Wine storage appliances">
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.bottleCapacity"/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>

  <@common.rescaledInternetLabellingFields/>
</@common.standardProductForm>
