<#include './layout.ftl'>

<#macro supplierNameModel>
  <@govukTextInput.textInput path="form.supplierName"/>
  <@govukTextInput.textInput path="form.modelName"/>
</#macro>

<#macro generateLabelButton>
  <@govukButton.button buttonText="Download label" buttonClass="govuk-button"/>
</#macro>

<#macro generateInternetLabelButton>
  <@govukButton.button buttonText="Download arrow image" buttonClass="govuk-button"/>
</#macro>

<#macro preMarch2021RadioItem legislationCategories>
  <@govukRadios.radioItem path="form.applicableLegislation" itemMap={"PRE_MAR2021": legislationCategories["PRE_MAR2021"]}>
    <#nested>
  </@govukRadios.radioItem>
</#macro>

<#macro postMarch2021RadioItem legislationCategories>
    <@govukRadios.radioItem path="form.applicableLegislation" itemMap={"POST_MAR2021": legislationCategories["POST_MAR2021"]}>
        <#nested>
        <@common.postMarch2021InternetLabellingFields/>
    </@govukRadios.radioItem>
</#macro>

<#-- Template for standard product forms.
Includes the wrapping form element, the generate label button and optionally the supplier name and model fields -->
<#macro standardProductForm title includeSupplierNameModel=true includePostMarch2021InternetLabellingFields=false>

  <@defaultPage pageHeading=title showInsetText=true>
    <@form.govukForm submitUrl + modeQueryParam!"">

      <#if includeSupplierNameModel>
        <@supplierNameModel/>
      </#if>

      <#nested/>

      <#if labelMode?has_content && labelMode == 'INTERNET'>
        <@govukTextInput.textInput path="form.productPriceHeightPx"/>
        <#if includePostMarch2021InternetLabellingFields>
            <@common.postMarch2021InternetLabellingFields/>
        </#if>
        <@govukRadios.radio path="form.labelOrientation" radioItems=internetLabelOrientationOptions/>
        <@govukRadios.radio path="form.labelFormat" radioItems=internetLabelFormatOptions/>
        <@generateInternetLabelButton/>
      <#else>

        <#if staticProductText?has_content>
          <div class="govuk-inset-text">
            ${staticProductText?no_esc}
          </div>
        <#elseif commonProductGuidance?has_content>
          <div class="govuk-inset-text">
            ${commonProductGuidance?no_esc}
          </div>
        </#if>

        <@generateLabelButton/>
      </#if>

    <input type="hidden" id="googleAnalyticsClientId" name="googleAnalyticsClientId" value="">
    </@form.govukForm>

    <#if googleAnalyticsEnabled>
      <script src="<@spring.url'/assets/scripts/googleAnalyticsSetClient.js'/>"></script>
    </#if>

  </@defaultPage>
</#macro>

<#macro postMarch2021InternetLabellingFields>
  <@govukRadios.radio path="form.labelColour" radioItems=internetLabelColourOptions/>
</#macro>
