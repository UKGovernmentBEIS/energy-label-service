<#include '../../layout.ftl'>
<#import 'waterSolarPackagesCalculatorCommon.ftl' as waterPackagesCommon>

<@common.standardProductForm
    title="Packages of water heater and solar device energy label calculator"
    showInsetText=false
    isPackageCalculatorForm=true
>
  <@waterPackagesCommon.commonWaterSolarPackagesFields/>
</@common.standardProductForm>