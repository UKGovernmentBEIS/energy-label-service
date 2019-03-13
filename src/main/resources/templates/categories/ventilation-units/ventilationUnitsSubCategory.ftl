<#include '../../layout.ftl'>

<@defaultPage pageHeading="What type of ventilation unit do you need a label for?">
  <@form.govukForm "/categories/ventilation-units/">

    <@govukRadios.radio path="form.subCategory" label="" radioItems=subCategories />

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>