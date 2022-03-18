<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as combinationSpaceHeatercommonFields>
<#import 'boilerPackageAndCombinationCommon.ftl' as boilerCommon>
<#import '../water-heaters/waterSolarPackagesCalculatorCommon.ftl' as waterPackagesCommon>

<@common.standardProductForm
title="Packages of combination heater, temperature control and solar device label calculator: ${preferentialHeater}"
showInsetText=false
isPackageCalculatorForm=true
>
    <@combinationSpaceHeatercommonFields.commonSpaceHeaterPackagesCalculatorFields/>
    <@boilerCommon.commonBoilerPackageAndCombinationFields/>
    <@waterPackagesCommon.commonWaterSolarPackagesFields/>
</@common.standardProductForm>