<#--GOVUK Button Widget-->
<#--https://design-system.service.gov.uk/components/button/-->
<#macro button buttonText buttonClass="" disabled=false buttonElement="button" buttonUrl="#">
  <#if buttonElement="link">
    <a href="${buttonUrl}" class="govuk-button ${buttonClass}">${buttonText}</a>
    <#else>
    <button type="submit" class="govuk-button<#if disabled>govuk-button--disabled</#if> ${buttonClass}" <#if disabled>disabled="disabled" aria-disabled="true"</#if> value="${buttonText}" name="${buttonText}">
      ${buttonText}
    </button>
  </#if>
</#macro>