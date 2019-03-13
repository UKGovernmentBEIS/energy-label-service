<#include '../../layout.ftl'>

<@defaultPage>
  <@form.govukForm "/categories/lamps/">

    <@govukRadios.radio path="form.subCategory" radioItems=subCategories label="What type of lamp do you need a label for?" legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>