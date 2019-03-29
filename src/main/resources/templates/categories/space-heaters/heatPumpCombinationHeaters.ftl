<#include '../../layout.ftl'>

<@common.standardProductForm title="Heat pump combination heaters">

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears/>

    <@govukSelect.select path="form.spaceHeatingEfficiencyRating" options=efficiencyRating/>
    <@govukSelect.select path="form.waterHeatingEfficiencyRating" options=secondaryEfficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

    <@govukRadios.radioYesNo path="form.offPeak"/>

    <@govukFieldset.fieldset legendHeading="The total rated heat output in kW" legendSize="h2" showInInternetLabelling=false>
      <@govukTextInput.textInput path="form.colderHeatOutput"/>
      <@govukTextInput.textInput path="form.averageHeatOutput"/>
      <@govukTextInput.textInput path="form.warmerHeatOutput"/>
    </@govukFieldset.fieldset>

</@common.standardProductForm>