<#include '../../layout.ftl'>

<@common.standardProductForm title="Conventional water heaters">

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukTextInput.textInput path="form.kwhAnnum"/>
    <@govukTextInput.textInput path="form.gjAnnum"/>

    <@govukRadios.radioYesNo path="form.offPeak"/>

</@common.standardProductForm>