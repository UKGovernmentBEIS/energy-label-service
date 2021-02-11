<#include '../../layout.ftl'>

<@common.standardProductForm "Washing machines">

  <#assign radioGroupClass>
    <#if labelMode=='ENERGY'>
      govuk-!-margin-bottom-0
    <#else>
      govuk-!-margin-bottom-2
    </#if>
  </#assign>

  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2" formGroupClass=radioGroupClass>

    <@common.postMarch2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.qrCodeUrl"/>
      <@govukTextInput.textInput path="form.energyConsumptionPer100Cycles"/>
        <@govukTextInput.textInput path="form.ecoRatedCapacity"/>
        <@govukTextInput.textInput path="form.waterConsumptionPerCycle"/>
        <#if labelMode=='ENERGY'>
          <div class="govuk-form-group">
            <@govukFieldset.fieldset legendHeading="Duration of the eco 40-60 programme at rated capacity" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
              <@govukTextInput.textInput path="form.programmeDurationHours"/>
              <@govukTextInput.textInput path="form.programmeDurationMinutes"/>
            </@govukFieldset.fieldset>
          </div>
        </#if>
        <@govukSelect.select path="form.noiseEmissionClass" options=noiseClass/>
        <@govukTextInput.textInput path="form.noiseEmissionValue"/>

    </@common.postMarch2021RadioItem>

    <@common.preMarch2021RadioItem legislationCategories>
        <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
        <@govukTextInput.textInput path="form.annualWaterConsumption"/>
        <@govukTextInput.textInput path="form.capacity"/>
        <@govukTextInput.textInput path="form.washingNoiseEmissions"/>
        <@govukTextInput.textInput path="form.spinningNoiseEmissions"/>
    </@common.preMarch2021RadioItem>
  </@govukRadios.radioGroup>
  <@common.labelTypeGuidanceMarch2021/>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukSelect.select path="form.spinDryingEfficiencyRating" options=spinDryingEfficiencyRating/>

</@common.standardProductForm>
