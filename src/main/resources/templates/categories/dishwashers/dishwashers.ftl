<#include '../../layout.ftl'>

<@common.standardProductForm "Dishwashers">
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
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
  <@govukTextInput.textInput path="form.noiseEmissions"/>
  <@common.rescaledInternetLabellingFields/>
</@common.standardProductForm>
