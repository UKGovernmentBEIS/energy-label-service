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

  <@defaultPage pageHeading=title showInsetText=true>
    <@form.govukForm submitUrl + modeQueryParam!"">

      <#if includeSupplierNameModel>
        <@supplierNameModel/>
      </#if>

      <#nested/>

      <#if labelMode?has_content && labelMode == 'INTERNET'>
        <@govukTextInput.textInput path="form.productPriceHeightPx"/>
        <@govukRadios.radio path="form.labelOrientation" radioItems=internetLabelOrientationOptions/>
        <@govukRadios.radio path="form.labelFormat" radioItems=internetLabelFormatOptions/>
      <#else>
        <#if staticProductText?has_content>
          <div class="govuk-inset-text">
            ${staticProductText?no_esc}
          </div>
        </#if>

        <#if commonProductGuidance?has_content>
          <div class="govuk-inset-text">
            ${commonProductGuidance?no_esc}
          </div>
        </#if>
      </#if>

      <@generateLabelButton/>

    </@form.govukForm>
  </@defaultPage>
</#macro>
