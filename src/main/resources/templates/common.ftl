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
        <@common.rescaledInternetLabellingFields/>
    </@govukRadios.radioItem>
</#macro>

<#macro preSeptember2021RadioItem legislationCategories>
    <@govukRadios.radioItem path="form.applicableLegislation" itemMap={"PRE_SEPT2021": legislationCategories["PRE_SEPT2021"]}>
        <#nested>
    </@govukRadios.radioItem>
</#macro>

<#macro postSeptember2021RadioItem legislationCategories>
    <@govukRadios.radioItem path="form.applicableLegislation" itemMap={"POST_SEPT2021": legislationCategories["POST_SEPT2021"]}>
        <#nested>
        <@common.rescaledInternetLabellingFields/>
    </@govukRadios.radioItem>
</#macro>

<#-- Template for standard product forms.
Includes the wrapping form element, the generate label button and optionally the supplier name and model fields -->
<#macro standardProductForm title includeSupplierNameModel=true includeRescaledInternetLabellingFields=false showInsetText=true>

  <@defaultPage pageHeading=title showInsetText=showInsetText>
    <@form.govukForm submitUrl + modeQueryParam!"">

      <#if includeSupplierNameModel>
        <@supplierNameModel/>
      </#if>

      <#nested/>

      <#if labelMode == 'INTERNET'>
        <@govukTextInput.textInput path="form.productPriceHeightPx"/>
        <@govukDetails.details summaryTitle="Why do I need to enter the height of the product's price?">
          <p>
            If you use an arrow image to link to the label, the energy efficiency class on the arrow must have the same
            font size as the product's price. We'll adjust the arrow image so that the height of the energy efficiency class
            matches the height of the price that you enter here.
          </p>
          <#if showRescaledInternetLabelGuidance>
            <p>
              If you're using the arrow image in visual advertisements, promotional material or paper-based distance
              selling materials, the font size of the energy efficiency class must be at least as large as the
              product's price when the price is shown. If the price isn't shown, the arrow must still be clearly
              visible and legible.
            </p>
          </#if>
        </@govukDetails.details>
        <#if includeRescaledInternetLabellingFields>
          <@common.rescaledInternetLabellingFields/>
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

  </@defaultPage>
</#macro>

<#macro rescaledInternetLabellingFields>
  <@govukRadios.radio path="form.labelColour" radioItems=internetLabelColourOptions/>
</#macro>

<#macro labelTypeGuidanceMarch2021>
  <@govukDetails.details summaryTitle="What kind of label should I choose?" detailsClass="govuk-!-margin-bottom-5">
    <p>
      If the product was first placed on the market on or after 1 November 2020, or hasn't been placed on the
      market yet, you must use the new rescaled energy label.
    </p>
    <p>
      The product's energy efficiency class on the rescaled label will be lower than its class on the old label, and
      other values might also be calculated differently. Before you create a rescaled label, you need to make sure
      the values you're entering are based on the criteria for the rescaled label. You shouldn't copy values directly
      from the old label.
    </p>
    <p>
      Products placed on the market before 1 November 2020 can continue to use the old style label until
      30 November 2021.
    </p>
  </@govukDetails.details>
</#macro>
