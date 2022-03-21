<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as commonFields>

<@common.standardProductForm
title="Packages of space heater, temperature control and solar device energy label calculator: ${preferentialHeater}"
showInsetText=false
isPackageCalculatorForm=true
>
  <@govukTextInput.textInput path="form.preferentialHeaterHeatOutput"/>
  <@govukTextInput.textInput path="form.preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage"/>
  <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage"/>
  <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage"/>
  <@govukRadios.radioYesNo path="form.lowTemperatureHeatPump"/>
  <@commonFields.commonSpaceHeaterPackagesCalculatorFields showPreferentialHeaterInputs=false/>
</@common.standardProductForm>