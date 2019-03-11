<#--GOVUK Details-->
<#--https://design-system.service.gov.uk/components/details/-->
<#macro details summaryText summaryTitle>
  <details class="govuk-details">
    <summary class="govuk-details__summary">
      <span class="govuk-details__summary-text">
        ${summaryTitle}
      </span>
    </summary>
    <div class="govuk-details__text">
      ${summaryText}
    </div>
  </details>
</#macro>