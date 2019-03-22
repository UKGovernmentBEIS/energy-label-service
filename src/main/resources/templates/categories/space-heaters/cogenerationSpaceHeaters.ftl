<#include '../../layout.ftl'>

<@common.standardProductForm title="Boiler space heaters">

    <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.heatOutput"/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukRadios.radioYesNo path="form.electricityGeneration"/>
</@common.standardProductForm>