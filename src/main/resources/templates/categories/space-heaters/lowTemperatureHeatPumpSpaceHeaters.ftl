<#include '../../layout.ftl'>

<@common.standardProductForm title="Low-temperature heat pump space heaters">

    <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears legendSize="h2"/>

    <@govukSelect.select path="form.lowTempEfficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

    <@govukFieldset.fieldset legendHeading="The total rated heat output for low temperature application, in kW" legendSize="h2" legendHeadingClass="govuk-fieldset__legend--s" showInInternetLabelling=false>
      <@govukTextInput.textInput path="form.lowTempColderHeatOutput"/>
      <@govukTextInput.textInput path="form.lowTempAverageHeatOutput"/>
      <@govukTextInput.textInput path="form.lowTempWarmerHeatOutput"/>
    </@govukFieldset.fieldset>

</@common.standardProductForm>