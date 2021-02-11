<#include '../../layout.ftl'>

<@common.standardProductForm "Wine storage appliances">
  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2" formGroupClass="govuk-!-margin-bottom-2">
    <@common.postMarch2021RadioItem legislationCategories>
        <@govukTextInput.textInput path="form.qrCodeUrl"/>
        <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>
    </@common.postMarch2021RadioItem>

    <@common.preMarch2021RadioItem legislationCategories/>
  </@govukRadios.radioGroup>

  <@common.labelTypeGuidanceMarch2021/>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.bottleCapacity"/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
