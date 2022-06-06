<#--GOVUK Fieldset-->
<#--https://design-system.service.gov.uk/components/fieldset/-->
<#macro fieldset legendHeadingClass="govuk-fieldset__legend--l" legendHeading="" legendSize="h1" hintText="" hintTextId="" productGuidanceText="" mandatory=true showInInternetLabelling=true>
  <#-- TODO encapsulate fieldset definitions in Form object-->
  <#if (labelMode == 'INTERNET' && showInInternetLabelling) || labelMode == 'ENERGY'>
    <fieldset class="govuk-fieldset" <#if hintText?has_content>  aria-describedby="${hintTextId}-hint"</#if>>
    <legend class="govuk-fieldset__legend ${legendHeadingClass}">
    <#if legendHeading?has_content>
      <#if legendSize="h1">
        <h1 class="govuk-fieldset__heading">
        ${legendHeading} <#if !mandatory>(optional)</#if>
        </h1>
      <#elseif legendSize="h2">
        <h2 class="govuk-fieldset__heading">
        ${legendHeading} <#if !mandatory>(optional)</#if>
        </h2>
      <#elseif legendSize="h3">
        <h3 class="govuk-fieldset__heading">
        ${legendHeading} <#if !mandatory>(optional)</#if>
        </h3>
      </#if>
    </#if>
    </legend>
      <#if hintText?has_content>
        <div id="${hintTextId}-hint" class="govuk-hint">
          ${hintText}
        </div>
      </#if>
    <#if productGuidanceText?has_content>
      <div class="govuk-inset-text">
        ${productGuidanceText?no_esc}
      </div>
    </#if>
    <#nested>

    </fieldset>
  </#if>
</#macro>
