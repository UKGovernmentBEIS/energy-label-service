<#include '../../layout.ftl'>

<@common.standardProductForm title="Packages of space heater, temperature control and solar device">

    <@govukSelect.select path="form.heaterEfficiencyRating" options=packageEfficiencyRating/>

    <@govukSelect.select path="form.packageEfficiencyRating" options=packageEfficiencyRating/>

    <@govukRadios.radioYesNo path="form.solarCollector"/>
    <@govukRadios.radioYesNo path="form.hotWaterStorageTank"/>
    <@govukRadios.radioYesNo path="form.temperatureControl"/>
    <@govukRadios.radioYesNo path="form.spaceHeater"/>

</@common.standardProductForm>