<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Condenser tumble dryers" errorItems=errorList>
  <@form.govukForm submitUrl>

    <@govukTextInput.textInput path="form.supplierName"/>
    <@govukTextInput.textInput path="form.modelName"/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption"/>

    <@govukTextInput.textInput path="form.cycleTime"/>

    <@govukTextInput.textInput path="form.ratedCapacity"/>

    <@govukTextInput.textInput path="form.soundPowerLevel"/>

    <@govukSelect.select path="form.condensationEfficiencyRating" options=condensationEfficiencyRating/>

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>