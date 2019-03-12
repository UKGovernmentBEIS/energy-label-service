<#include './layout.ftl'>

<@defaultPage pageHeading="What type of item do you need a label for?">
  <@form.govukForm "/categories">

    <@govukRadios.radio path="form.category" label="" radioItems=categories />

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>