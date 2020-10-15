<#include '../../layout.ftl'>

<@common.standardProductForm title="Washer-dryers" includePostMarch2021InternetLabellingFields=true>
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.completeCycleEfficiencyRating" options=efficiencyRating/>
  <@govukSelect.select path="form.washingCycleEfficiencyRating" options=efficiencyRating/>

  <@govukTextInput.textInput path="form.completeCycleEnergyConsumption"/>
  <@govukTextInput.textInput path="form.washingCycleEnergyConsumption"/>

  <@govukTextInput.textInput path="form.completeCycleCapacity"/>
  <@govukTextInput.textInput path="form.washingCycleCapacity"/>

  <@govukTextInput.textInput path="form.completeCycleWaterConsumption"/>
  <@govukTextInput.textInput path="form.washingCycleWaterConsumption"/>
  <#if labelMode=='ENERGY'>
    <div class="govuk-form-group">
      <@govukFieldset.fieldset legendHeading="Cycle duration at rated capacity for the complete cycle" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.completeCycleDurationHours"/>
        <@govukTextInput.textInput path="form.completeCycleDurationMinutes"/>
      </@govukFieldset.fieldset>
    </div>

    <div class="govuk-form-group">
      <@govukFieldset.fieldset legendHeading="Cycle duration at rated capacity for the washing cycle" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.washingCycleDurationHours"/>
        <@govukTextInput.textInput path="form.washingCycleDurationMinutes"/>
      </@govukFieldset.fieldset>
    </div>
  </#if>
  <@govukSelect.select path="form.spinDryingEfficiencyRating" options=spinDryingEfficiencyRating/>
  <@govukSelect.select path="form.noiseEmissionClass" options=noiseClass/>
  <@govukTextInput.textInput path="form.noiseEmissionValue"/>

</@common.standardProductForm>
