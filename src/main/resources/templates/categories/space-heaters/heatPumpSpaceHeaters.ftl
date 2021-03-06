<#include '../../layout.ftl'>

<@common.standardProductForm title="Heat pump space heaters">

    <@govukSelect.select path="form.mediumTempEfficiencyRating" options=efficiencyRating/>
    <@govukSelect.select path="form.lowTempEfficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

    <@govukFieldset.fieldset legendHeading="The total rated heat output for medium temperature application, in kW" legendSize="h2" legendHeadingClass="govuk-fieldset__legend--s" showInInternetLabelling=false>
      <@govukTextInput.textInput path="form.mediumTempColderHeatOutput"/>
      <@govukTextInput.textInput path="form.mediumTempAverageHeatOutput"/>
      <@govukTextInput.textInput path="form.mediumTempWarmerHeatOutput"/>
    </@govukFieldset.fieldset>

    <@govukFieldset.fieldset legendHeading="The total rated heat output for low temperature application, in kW" legendSize="h2" legendHeadingClass="govuk-fieldset__legend--s" showInInternetLabelling=false>
      <@govukTextInput.textInput path="form.lowTempColderHeatOutput"/>
      <@govukTextInput.textInput path="form.lowTempAverageHeatOutput"/>
      <@govukTextInput.textInput path="form.lowTempWarmerHeatOutput"/>
    </@govukFieldset.fieldset>

</@common.standardProductForm>
