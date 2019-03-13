<#include './layout.ftl'>

<#assign title="What type of item do you need a label for?">

<@defaultPage pageTitle=title>
  <@form.govukForm "/categories">

    <@govukRadios.radio path="form.category" radioItems=categories label=title legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>