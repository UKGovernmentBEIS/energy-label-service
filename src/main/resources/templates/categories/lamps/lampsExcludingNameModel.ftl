<#include '../../layout.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Lamps">
  <@form.govukForm "/categories/lamps-excluding-name-model">

    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class of the application" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.energyConsumption" label="Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer"/>

    <@govukRadios.radio path="form.templateType" label="Which type of label should be generated? " radioItems=templateType />

    <@govukButton.button buttonText="Generate Label" buttonClass="govuk-button--start govuk-!-margin-top-2 govuk-!-margin-bottom-8"/>
  </@form.govukForm>

</@defaultPage>