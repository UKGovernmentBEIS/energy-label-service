<#include '../../layout.ftl'>

<@common.standardProductForm title="Packages of combination heater, temperature control and solar device">

    <@govukSelect.select path="form.spaceHeaterEfficiencyRating" options=packageEfficiencyRating/>
    <@govukSelect.select path="form.waterHeaterEfficiencyRating" options=packageEfficiencyRating/>

    <@govukSelect.select path="form.heaterDeclaredLoadProfile" options=loadProfile/>

    <@govukRadios.radioYesNo path="form.solarCollector"/>
    <@govukRadios.radioYesNo path="form.hotWaterStorageTank"/>
    <@govukRadios.radioYesNo path="form.temperatureControl"/>
    <@govukRadios.radioYesNo path="form.spaceHeater"/>

    <@govukSelect.select path="form.packageSpaceHeatingEfficiencyRating" options=packageEfficiencyRating/>
    <@govukSelect.select path="form.packageWaterHeatingEfficiencyRating" options=packageEfficiencyRating/>

    <@govukSelect.select path="form.packageDeclaredLoadProfile" options=loadProfile/>

</@common.standardProductForm>