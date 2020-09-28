<#include '../../layout.ftl'>

<@common.standardProductForm title="Cogeneration space heaters">

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.heatOutput"/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukRadios.radioYesNo path="form.electricityGeneration"/>
</@common.standardProductForm>
