<#--GOVUK Breadcrumbs-->
<#--https://design-system.service.gov.uk/components/breadcrumbs/-->
<#macro breadcrumbs crumbMap>
  <#if crumbMap?size gt 1 >
    <nav class="govuk-breadcrumbs" aria-label="Breadcrumb">
      <ol class="govuk-breadcrumbs__list">
      <#list crumbMap as key, value>
        <#if key?is_last>
          <li class="govuk-breadcrumbs__list-item" aria-current="true">${key}</li>
        <#else>
          <li class="govuk-breadcrumbs__list-item">
          <a class="govuk-breadcrumbs__link" href="${value}">${key}</a>
          </li>
        </#if>
      </#list>
      <#--<li class="govuk-breadcrumbs__list-item" aria-current="${currentPage}">${currentPage}</li>-->
      </ol>
    </nav>
  </#if>
</#macro>