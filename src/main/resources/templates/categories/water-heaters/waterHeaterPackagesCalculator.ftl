<#include '../../layout.ftl'>

<@common.standardProductForm
    title="Packages of water heater and solar device energy calculator"
    includeSupplierNameModel=false
    showInsetText=false
>
    <@govukTextInput.textInput path="form.waterHeatingEfficiencyPercentage"/>

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukRadios.radioYesNo path="form.storageTank"/>

    <@govukRadios.radioYesNo path="form.solarCollector"/>

    <@govukTextInput.textInput path="form.annualNonSolarHeatContribution"/>

    <@govukTextInput.textInput path="form.auxElectricityConsumption"/>
</@common.standardProductForm>

<#--<@defaultPage pageTitle="Do you need help calculating the energy rating for this package?">-->


<#--    <@govukTextInput.textInput path="form.waterHeatingEfficiencyPercentage"/>-->

<#--    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>-->

<#--    <@govukRadios.radioYesNo path="form.storageTank"/>-->

<#--    <@govukRadios.radioYesNo path="form.solarCollector"/>-->

<#--    <@govukTextInput.textInput path="form.annualNonSolarHeatContribution"/>-->

<#--    <@govukTextInput.textInput path="form.auxElectricityConsumption"/>-->

<#--</@defaultPage>-->