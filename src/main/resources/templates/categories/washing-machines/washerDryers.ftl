<#include '../../layout.ftl'>

<@common.standardProductForm title="Washer-dryers" includePostMarch2021InternetLabellingFields=true>
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.spinDryingEfficiencyRating" options=spinDryingEfficiencyRating/>
  <@govukSelect.select path="form.noiseEmissionClass" options=noiseClass/>
  <@govukTextInput.textInput path="form.noiseEmissionValue"/>

  <#if labelMode=='ENERGY'>
    <h2 class="govuk-heading-l">Complete cycle (wash and dry)</h2>
  </#if>
  <@govukSelect.select path="form.completeCycleEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.completeCycleEnergyConsumption"/>
  <@govukTextInput.textInput path="form.completeCycleCapacity"/>
  <@govukTextInput.textInput path="form.completeCycleWaterConsumption"/>
  <#if labelMode=='ENERGY'>
    <div class="govuk-form-group">
      <@govukFieldset.fieldset legendHeading="Cycle duration at rated capacity for the complete cycle" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.completeCycleDurationHours"/>
        <@govukTextInput.textInput path="form.completeCycleDurationMinutes"/>
      </@govukFieldset.fieldset>
    </div>
  </#if>

  <#if labelMode=='ENERGY'>
    <h2 class="govuk-heading-l">Washing cycle</h2>
  </#if>
  <@govukSelect.select path="form.washingCycleEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.washingCycleEnergyConsumption"/>
  <@govukTextInput.textInput path="form.washingCycleCapacity"/>
  <@govukTextInput.textInput path="form.washingCycleWaterConsumption"/>
  <#if labelMode=='ENERGY'>
    <div class="govuk-form-group">
      <@govukFieldset.fieldset legendHeading="Cycle duration at rated capacity for the washing cycle" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.washingCycleDurationHours"/>
        <@govukTextInput.textInput path="form.washingCycleDurationMinutes"/>
      </@govukFieldset.fieldset>
    </div>
  </#if>

</@common.standardProductForm>
