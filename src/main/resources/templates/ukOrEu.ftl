<#include './layout.ftl'>

<@defaultPage pageTitle=title>
  <@form.govukForm submitUrl>

    <@govukRadios.radio path="form.ukOrEuAnswer" radioItems=options label=title legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>
</@defaultPage>