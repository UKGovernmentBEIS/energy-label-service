<#include '../../layout.ftl'>

<@common.standardProductForm "Washing machines">
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
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukSelect.select path="form.spinDryingEfficiencyRating" options=spinDryingEfficiencyRating/>
  <@common.rescaledInternetLabellingFields/>
</@common.standardProductForm>
