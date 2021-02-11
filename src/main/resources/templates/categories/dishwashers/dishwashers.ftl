<#include '../../layout.ftl'>

<@common.standardProductForm "Dishwashers">

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
        <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>
        <@govukTextInput.textInput path="form.ecoCapacity"/>
        <@govukTextInput.textInput path="form.energyConsumptionPer100Cycles"/>
        <@govukTextInput.textInput path="form.waterConsumptionPerCycle"/>
        <#if labelMode=='ENERGY'>
          <div class="govuk-form-group">
              <@govukFieldset.fieldset legendHeading="Eco programme duration" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
                  <@govukTextInput.textInput path="form.programmeDurationHours"/>
                  <@govukTextInput.textInput path="form.programmeDurationMinutes"/>
              </@govukFieldset.fieldset>
          </div>
        </#if>
    </@common.postMarch2021RadioItem>

    <@common.preMarch2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.standardCapacity"/>
      <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
      <@govukTextInput.textInput path="form.annualWaterConsumption"/>
      <@govukSelect.select path="form.dryingEfficiencyRating" options=dryingEfficiencyRating/>
    </@common.preMarch2021RadioItem>
  </@govukRadios.radioGroup>

  <@common.labelTypeGuidanceMarch2021/>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
