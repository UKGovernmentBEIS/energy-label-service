<#include '../../layout.ftl'>

<@defaultPage>
  <@form.govukForm "/categories/ventilation-units/">

    <@govukRadios.radio path="form.subCategory" label="What type of ventilation unit do you need a label for?" radioItems=subCategories legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>