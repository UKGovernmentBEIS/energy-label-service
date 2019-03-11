<#--GOVUK Fieldset-->
<#--https://design-system.service.gov.uk/components/fieldset/-->
<#macro fieldset legendHeadingClass="govuk-fieldset__legend--l" legendHeading="" legendSize="h1" mandatory=true>
  <fieldset class="govuk-fieldset">
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

    <#nested>

  </fieldset>
</#macro>