<#include '../../layout.ftl'>

<@common.standardProductForm "Wine storage appliances">
  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2">
    <@common.preMarch2021RadioItem legislationCategories/>

    <@common.postMarch2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.qrCodeUrl"/>
      <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>
    </@common.postMarch2021RadioItem>
  </@govukRadios.radioGroup>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.bottleCapacity"/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
