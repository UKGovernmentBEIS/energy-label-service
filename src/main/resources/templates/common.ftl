<#include './layout.ftl'>

<#macro supplierNameModel>
  <@govukTextInput.textInput path="form.supplierName"/>
  <@govukTextInput.textInput path="form.modelName"/>
</#macro>

<#macro generateLabelButton>
  <@govukButton.button buttonText="Download label" buttonClass="govuk-button"/>
</#macro>

<#macro generateInternetLabelButton>
  <@govukButton.button buttonText="Download internet label" buttonClass="govuk-button"/>
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
      <script>
        ga(function(tracker) {
          var clientId = tracker.get('clientId');
          document.getElementById('googleAnalyticsClientId').value = clientId;
        });
      </script>
    </#if>

  </@defaultPage>
</#macro>
