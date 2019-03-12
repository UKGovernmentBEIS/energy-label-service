<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Lamps">
  <@form.govukForm submitUrl>

    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class of the application" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption" label="Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer"/>

    <@govukRadios.radio path="form.templateType" label="Which type of label should be generated? " radioItems=templateType />

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>