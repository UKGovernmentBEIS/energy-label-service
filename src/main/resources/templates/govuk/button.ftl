<#--GOVUK Button Widget-->
<#--https://design-system.service.gov.uk/components/button/-->
<#macro button buttonText buttonClass="" disabled=false buttonElement="button" buttonUrl="#">
  <#if buttonElement="link">
    <a href="${buttonUrl}" class="govuk-button ${buttonClass}">${buttonText}</a>
    <#else>
    <button type="submit" class="govuk-button<#if disabled>govuk-button--disabled</#if> ${buttonClass}" <#if disabled>disabled="disabled" aria-disabled="true"</#if> value="${buttonText}" name="${buttonText}" data-prevent-double-click="true" data-module="govuk-button">
      ${buttonText}
    </button>
  </#if>
</#macro>

<#macro buttonWithSecondaryAction primaryButtonText secondaryButtonText primaryButtonClass="" primaryButtonUrl="#" secondaryButtonClass="govuk-button--secondary" secondaryButtonUrl="#" buttonsDisabled=false>
    <@button buttonText=primaryButtonText buttonClass=primaryButtonClass buttonUrl=primaryButtonUrl disabled=buttonsDisabled/>
    <@button buttonText=secondaryButtonText buttonClass=secondaryButtonClass buttonUrl=secondaryButtonUrl disabled=buttonsDisabled/>
</#macro>
