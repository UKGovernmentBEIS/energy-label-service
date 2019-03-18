<#include '../../layout.ftl'>

<@common.standardProductForm title="Hot water storage tanks">

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.standingLoss"/>

    <@govukTextInput.textInput path="form.volume"/>


</@common.standardProductForm>