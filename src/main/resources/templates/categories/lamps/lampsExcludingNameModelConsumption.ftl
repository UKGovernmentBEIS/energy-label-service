<#include '../../layout.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Lamps">
  <@form.govukForm "/categories/lamps-excluding-name-model-consumption">

    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class of the application" options=efficiencyRating/>

    <@govukRadios.radio path="form.templateType" label="Which type of label should be generated? " radioItems=templateType />

    <@govukButton.button buttonText="Generate Label" buttonClass="govuk-button--start govuk-!-margin-top-2 govuk-!-margin-bottom-8"/>
  </@form.govukForm>

</@defaultPage>