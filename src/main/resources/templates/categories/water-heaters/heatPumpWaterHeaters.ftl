<#include '../../layout.ftl'>

<@common.standardProductForm title="Heat pump water heaters">

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

    <@govukFieldset.fieldset legendHeading="Annual electricity consumption in kWh/annum" legendSize="h2" showInInternetLabelling=false>
      <@govukTextInput.textInput path="form.colderKwhAnnum"/>
      <@govukTextInput.textInput path="form.averageKwhAnnum"/>
      <@govukTextInput.textInput path="form.warmerKwhAnnum"/>
    </@govukFieldset.fieldset>

    <@govukFieldset.fieldset legendHeading="Annual fuel consumption in GJ/annum" legendSize="h2" showInInternetLabelling=false>
      <@govukTextInput.textInput path="form.colderGjAnnum"/>
      <@govukTextInput.textInput path="form.averageGjAnnum"/>
      <@govukTextInput.textInput path="form.warmerGjAnnum"/>
    </@govukFieldset.fieldset>

    <@govukRadios.radioYesNo path="form.canRunOffPeakOnly"/>

</@common.standardProductForm>