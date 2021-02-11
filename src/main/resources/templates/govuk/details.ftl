<#--GOVUK Details-->
<#--https://design-system.service.gov.uk/components/details/-->
<#macro details summaryTitle detailsClass="">
  <details class="govuk-details<#if detailsClass?has_content> ${detailsClass}</#if>">
    <summary class="govuk-details__summary">
      <span class="govuk-details__summary-text">
        ${summaryTitle}
      </span>
    </summary>
    <div class="govuk-details__text">
      <#nested>
    </div>
  </details>
</#macro>
