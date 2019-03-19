<#include '../../layout.ftl'>

<@common.standardProductForm title="Heat pump water heaters">

    <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears/>

    <@govukSelect.select path="form.lowTempEfficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

    <@govukFieldset.fieldset legendHeading="The total rated heat output for low temperature application, in kW">
      <@govukTextInput.textInput path="form.lowTempColderHeatOutput"/>
      <@govukTextInput.textInput path="form.lowTempAverageHeatOutput"/>
      <@govukTextInput.textInput path="form.lowTempWarmerHeatOutput"/>
    </@govukFieldset.fieldset>

</@common.standardProductForm>