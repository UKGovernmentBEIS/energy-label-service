<#include '../../layout.ftl'>

<@common.standardProductForm title="Packages of water heater and solar device">

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukSelect.select path="form.heaterEfficiencyRating" options=secondaryEfficiencyRating/>
    <@govukSelect.select path="form.packageEfficiencyRating" options=efficiencyRating/>

    <@govukRadios.radioYesNo path="form.solarCollector"/>
    <@govukRadios.radioYesNo path="form.storageTank"/>

</@common.standardProductForm>