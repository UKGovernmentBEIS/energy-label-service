<#include '../../layout.ftl'>

<@common.standardProductForm title="Packages of a solid fuel boiler, supplementary heaters, temperature controls and solar devices">

    <@govukSelect.select path="form.boilerEfficiencyRating" options=efficiencyRating/>

    <@govukSelect.select path="form.packageEfficiencyRating" options=efficiencyRating/>

    <@govukRadios.radioYesNo path="form.solarCollector"/>
    <@govukRadios.radioYesNo path="form.hotWaterStorageTank"/>
    <@govukRadios.radioYesNo path="form.temperatureControl"/>
    <@govukRadios.radioYesNo path="form.spaceHeater"/>

</@common.standardProductForm>