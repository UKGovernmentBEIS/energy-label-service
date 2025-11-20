<#include '../../layout.ftl'>
<@common.standardProductForm title="Tumble dryers" includeRescaledInternetLabellingFields=true showInternetLabelOrientation=false>
    <@govukTextInput.textInput path="form.qrCodeUrl"/>
    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
    <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsClass/>
    <@govukTextInput.textInput path="form.ecoCapacity"/>
    <@govukTextInput.textInput path="form.energyConsumptionPer100Cycles"/>
    <#if labelMode=='ENERGY'>
      <div class="govuk-form-group">
          <@govukFieldset.fieldset legendHeading="Duration of the eco programme at full load rounded to the nearest minute" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
              <@govukTextInput.textInput path="form.programmeDurationHours"/>
              <@govukTextInput.textInput path="form.programmeDurationMinutes"/>
          </@govukFieldset.fieldset>
      </div>
    </#if>
    <@govukTextInput.textInput path="form.noiseEmissions"/>
    <@govukRadios.radioYesNo path="form.isCondensing" hiddenQuestionsWithYesSelected=true>
        <@govukSelect.select path="form.condensationEfficiencyClass" options=condensationEfficiencyClass/>
        <@govukTextInput.textInput path="form.condensationEfficiencyPercentage"/>
    </@govukRadios.radioYesNo>
</@common.standardProductForm>