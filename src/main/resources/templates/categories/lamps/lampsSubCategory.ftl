<#include '../../layout.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="What type of lamp do you need a label for?">
  <@form.govukForm "/categories/lamps/">

    <@govukRadios.radio path="form.subCategory" label="" radioItems=subCategories />

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>