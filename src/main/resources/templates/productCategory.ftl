<#include './layout.ftl'>

<@defaultPage>
  <@form.govukForm "/categories">

    <@govukRadios.radio path="form.category" radioItems=categories label="What type of item do you need a label for?" legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>