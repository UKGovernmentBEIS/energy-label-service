<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Lamps">
  <@form.govukForm submitUrl>

    <@govukTextInput.textInput path="form.supplierName" label="Supplier's name or trade mark"/>
    <@govukTextInput.textInput path="form.modelName" label="Supplier's model identifier"/>

    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class of the application" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption" label="Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer"/>

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>