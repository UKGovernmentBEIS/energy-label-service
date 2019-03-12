<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Lamps">
  <@form.govukForm submitUrl>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption"/>

    <@govukRadios.radio path="form.templateType" radioItems=templateType />

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>