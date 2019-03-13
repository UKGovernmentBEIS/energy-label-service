<#include '../../layout.ftl'>

<#assign title="What type of label do you need for this lamp?">

<@defaultPage pageTitle=title errorItems=errorList>
  <@form.govukForm "/categories/lamps/">

    <@govukRadios.radio path="form.subCategory" radioItems=subCategories label=title legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>