<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as commonFields>

<@common.standardProductForm
title="Packages of space heater, temperature control and solar device energy label calculator"
showInsetText=false
>
  <@commonFields.commonSpaceHeaterPackagesCalculatorFields/>
  <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage"/>
  <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage"/>
  <@govukRadios.radioYesNo path="form.lowTemperatureHeatPump"/>
</@common.standardProductForm>