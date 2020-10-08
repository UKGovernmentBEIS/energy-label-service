<#include '../../layout.ftl'>

<@common.standardProductForm "Dishwashers">
  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2">
    <@common.preMarch2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.standardCapacity"/>
      <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
      <@govukTextInput.textInput path="form.annualWaterConsumption"/>
      <@govukSelect.select path="form.dryingEfficiencyRating" options=dryingEfficiencyRating/>
    </@common.preMarch2021RadioItem>

    <@common.postMarch2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.qrCodeUrl"/>
      <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>
      <@govukTextInput.textInput path="form.ecoCapacity"/>
      <@govukTextInput.textInput path="form.energyConsumptionPer100Cycles"/>
      <@govukTextInput.textInput path="form.waterConsumptionPerCycle"/>
      <div class="govuk-form-group">
        <@govukFieldset.fieldset legendHeading="Eco programme duration" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
          <@govukTextInput.textInput path="form.programmeDurationHours"/>
          <@govukTextInput.textInput path="form.programmeDurationMinutes"/>
        </@govukFieldset.fieldset>
      </div>
    </@common.postMarch2021RadioItem>
  </@govukRadios.radioGroup>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
