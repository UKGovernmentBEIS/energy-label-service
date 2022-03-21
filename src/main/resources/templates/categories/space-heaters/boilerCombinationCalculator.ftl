<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as combinationSpaceHeatercommonFields>
<#import 'boilerPackageAndCombinationCommon.ftl' as boilerCommon>

<@common.standardProductForm
title="Packages of combination heater, temperature control and solar device label calculator: ${preferentialHeater}"
showInsetText=false
isPackageCalculatorForm=true
>
    <@combinationSpaceHeatercommonFields.commonSpaceHeaterPackagesCalculatorFields showSolarCollector=false/>

    <@govukRadios.radioYesNo path="form.hasSolarCollector" hiddenQuestionsWithYesSelected=true legendSize="h3">
        <@govukTextInput.textInput path="form.solarCollectorSize"/>
        <@govukTextInput.textInput path="form.solarCollectorEfficiencyPercentage"/>
        <@govukTextInput.textInput path="form.annualNonSolarHeatContribution"/>
        <@govukTextInput.textInput path="form.auxElectricityConsumption"/>
    </@govukRadios.radioYesNo>

    <@boilerCommon.commonBoilerPackageAndCombinationFields/>

    <@govukFieldset.fieldset legendHeading="Water heater" legendSize="h2">
        <@govukTextInput.textInput path="form.waterHeatingEfficiencyPercentage"/>
        <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>
    </@govukFieldset.fieldset>
</@common.standardProductForm>