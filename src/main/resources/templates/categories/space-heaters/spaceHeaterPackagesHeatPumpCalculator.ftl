<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as commonFields>
<#import 'heatPumpPackageAndCombinationCommon.ftl' as heatPumpCommon>

<@common.standardProductForm
title="Packages of space heater, temperature control and solar device energy label calculator: ${preferentialHeater}"
showInsetText=false
isPackageCalculatorForm=true
>
  <@heatPumpCommon.commonHeatPumpPackageAndCombinationFields/>
  <@commonFields.commonSpaceHeaterPackagesCalculatorFields showPreferentialHeaterInputs=false/>
</@common.standardProductForm>