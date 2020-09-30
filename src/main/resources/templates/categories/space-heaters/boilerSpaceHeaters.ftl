<#include '../../layout.ftl'>

<@common.standardProductForm title="Boiler space heaters">

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.heatOutput"/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>
</@common.standardProductForm>
