<#include '../../layout.ftl'>

<@common.standardProductForm title="Solid fuel boilers">

    <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.ratedHeatOutput"/>

    <@govukRadios.radioYesNo path="form.combination"/>
    <@govukRadios.radioYesNo path="form.cogeneration"/>

</@common.standardProductForm>