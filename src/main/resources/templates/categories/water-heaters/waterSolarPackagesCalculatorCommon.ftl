<#include '../../layout.ftl'>

<#macro commonWaterSolarPackagesFields>
  <@govukFieldset.fieldset legendHeading="Water heater" legendSize="h2">
    <@govukTextInput.textInput path="form.waterHeatingEfficiencyPercentage"/>
    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>
  </@govukFieldset.fieldset>

  <@govukRadios.radioYesNo path="form.storageTank"/>

  <@govukFieldset.fieldset legendHeading="Solar device" legendSize="h2">
    <@govukTextInput.textInput path="form.annualNonSolarHeatContribution"/>
    <@govukTextInput.textInput path="form.auxElectricityConsumption"/>
  </@govukFieldset.fieldset>
</#macro>