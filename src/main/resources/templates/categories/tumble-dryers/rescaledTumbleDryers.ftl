<#include '../../layout.ftl'>
<#assign easementInsetText>
  Now you have chosen to use the easement label, you will also need to use the product information sheet which accords with it.
  You can <a href="<@spring.url'/assets/files/example.txt'/>" class="govuk-link" target="_blank">download a template product information sheet</a> to complete.
</#assign>
<@common.standardProductForm title="Tumble dryers" includeRescaledInternetLabellingFields=true showInternetLabelOrientation=false beforeStandardInsetText=easementInsetText>
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