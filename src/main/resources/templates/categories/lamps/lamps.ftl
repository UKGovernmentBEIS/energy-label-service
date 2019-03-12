<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Lamps">
  <@form.govukForm submitUrl>

    <@govukTextInput.textInput path="form.supplierName"/>
    <@govukTextInput.textInput path="form.modelName"/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption"/>

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>