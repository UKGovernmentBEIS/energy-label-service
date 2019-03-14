<#include './layout.ftl'>

<#macro supplierNameModel>
  <@govukTextInput.textInput path="form.supplierName"/>
  <@govukTextInput.textInput path="form.modelName"/>
</#macro>

<#macro generateLabelButton>
  <@govukButton.button buttonText="Generate label" buttonClass="govuk-button"/>
</#macro>

<#-- Template for standard product forms.
Includes the wrapping form element, the generate label button and optionally the supplier name and model fields -->
<#macro standardProductForm title includeSupplierNameModel=true>
  <@defaultPage pageHeading=title>
    <@form.govukForm submitUrl>

      <#if includeSupplierNameModel>
        <@supplierNameModel/>
      </#if>

      <#nested/>

      <@generateLabelButton/>

    </@form.govukForm>
  </@defaultPage>
</#macro>
