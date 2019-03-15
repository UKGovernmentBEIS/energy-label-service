<#include './layout.ftl'>

<@defaultPage pageTitle=title>
  <@form.govukForm submitUrl>

    <@govukRadios.radio path="form.category" radioItems=categories label=title guidanceText=categoryGuidanceText legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>