<#include '../../layout.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Lamps">
  <@form.govukForm submitUrl>

    <@govukTextInput.textInput path="form.supplierName" label="Supplier's name or trade mark"/>
    <@govukTextInput.textInput path="form.modelName" label="Supplier's model identifier"/>

    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class of the application" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption" label="Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer"/>

    <@govukButton.button buttonText="Generate Label" buttonClass="govuk-button--start govuk-!-margin-top-2 govuk-!-margin-bottom-8"/>
  </@form.govukForm>

</@defaultPage>