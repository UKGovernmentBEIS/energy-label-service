<#--Accordion Component-->
<#-- https://design-system.service.gov.uk/components/accordion/ -->
<#global accordionCounter></#global>
<#global accordionGlobalId></#global>

<#macro accordion accordionId>
    <#assign accordionCounter = 1>
    <#assign accordionGlobalId = accordionId>
  <div class="govuk-accordion" data-module="govuk-accordion" id="${accordionGlobalId}">
      <#nested>
  </div>
</#macro>

<#macro accordionSection sectionHeading sectionHeadingSize="h2" summaryText="">
  <div class="govuk-accordion__section ">
    <div class="govuk-accordion__section-header">
      <${sectionHeadingSize} class="govuk-accordion__section-heading">
      <span class="govuk-accordion__section-button" id="${accordionGlobalId}-heading-${accordionCounter}">${sectionHeading}</span>
    </${sectionHeadingSize}>
      <#if summaryText?has_content>
        <div class="govuk-accordion__section-summary govuk-body" id="${accordionGlobalId}-summary-${accordionCounter}">
            ${summaryText}
        </div>
      </#if>
  </div>
  <div id="${accordionGlobalId}-content-${accordionCounter}" class="govuk-accordion__section-content" aria-labelledby="${accordionGlobalId}-heading-${accordionCounter}">
      <#nested>
  </div>
  </div>
    <#assign accordionCounter++>
</#macro>
