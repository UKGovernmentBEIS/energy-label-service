<#include '../../layout.ftl'>

<@defaultPage>
  <@form.govukForm "/categories/lamps/">

    <@govukRadios.radio path="form.subCategory" radioItems=subCategories label="What type of label do you need?" legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>