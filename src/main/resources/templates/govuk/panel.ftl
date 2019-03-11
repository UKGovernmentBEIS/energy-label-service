<#--GOVUK Panel-->
<#--https://design-system.service.gov.uk/components/panel/-->
<#macro panel panelTitle panelText="" panelRef="">
  <div class="govuk-panel govuk-panel--confirmation">
    <#if panelTitle?has_content>
      <h1 class="govuk-panel__title">
        ${panelTitle}
      </h1>
    </#if>
    <div class="govuk-panel__body">
      ${panelText}
      <#if panelRef?has_content>
        <br><strong>${panelRef}</strong>
      </#if>
    </div>
  </div>
</#macro>
