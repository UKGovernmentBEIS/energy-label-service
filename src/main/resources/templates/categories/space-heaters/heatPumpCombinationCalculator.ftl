<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as combinationSpaceHeatercommonFields>
<#import 'heatPumpPackageAndCombinationCommon.ftl' as heatPumpCommon>

<@common.standardProductForm
title="Packages of combination heater, temperature control and solar device label calculator: ${preferentialHeater}"
showInsetText=false
isPackageCalculatorForm=true
>
    <@heatPumpCommon.commonHeatPumpPackageAndCombinationFields/>

    <@govukFieldset.fieldset legendHeading="Solar collector" legendSize="h2">
        <@govukTextInput.textInput path="form.solarCollectorSize"/>
        <@govukTextInput.textInput path="form.solarCollectorEfficiencyPercentage"/>
        <@govukTextInput.textInput path="form.annualNonSolarHeatContribution"/>
        <@govukTextInput.textInput path="form.auxElectricityConsumption"/>
    </@govukFieldset.fieldset>

    <@govukFieldset.fieldset legendHeading="Water heater" legendSize="h2">
        <@govukTextInput.textInput path="form.waterHeatingEfficiencyPercentage"/>
        <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>
    </@govukFieldset.fieldset>

    <@combinationSpaceHeatercommonFields.commonSpaceHeaterPackagesCalculatorFields
    showPreferentialHeaterInputs=false
    showSolarCollector=false
    />
</@common.standardProductForm>