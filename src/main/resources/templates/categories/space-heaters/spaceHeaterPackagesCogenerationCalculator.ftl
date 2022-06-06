<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as commonFields>

<@common.standardProductForm
title="Packages of space heater, temperature control and solar device energy label calculator: ${preferentialHeater}"
showInsetText=false
isPackageCalculatorForm=true
>
  <@commonFields.commonSpaceHeaterPackagesCalculatorFields/>
</@common.standardProductForm>