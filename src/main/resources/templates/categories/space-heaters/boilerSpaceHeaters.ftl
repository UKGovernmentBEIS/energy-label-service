<#include '../../layout.ftl'>

<@common.standardProductForm title="Boiler space heaters">

    <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears legendSize="h2"/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.heatOutput"/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>
</@common.standardProductForm>