<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as commonFields>

<@common.standardProductForm
title="Packages of space heater, temperature control and solar device energy label calculator"
showInsetText=false
>
  <@commonFields.commonSpaceHeaterPackagesCalculatorFields/>

  <@govukFieldset.fieldset legendHeading="Heat pump" legendSize="h2">
    <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage"/>
    <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage"/>
    <@govukRadios.radioYesNo path="form.lowTemperatureHeatPump"/>
  </@govukFieldset.fieldset>
</@common.standardProductForm>