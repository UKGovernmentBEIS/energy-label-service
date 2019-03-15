<#--GOVUK Fieldset-->
<#--https://design-system.service.gov.uk/components/fieldset/-->
<#macro fieldset legendHeadingClass="govuk-fieldset__legend--l" legendHeading="" legendSize="h1" productGuidanceText="" mandatory=true>
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
      <#if productGuidanceText?has_content>
        <div class="govuk-inset-text">
          ${productGuidanceText?no_esc}
          <p>
            You can also <a class="govuk-link" href="/not-yet-implemented">generate a nested arrow</a> for products sold via the internet.
          </p>
        </div>
      </#if>
    </legend>

    <#nested>

  </fieldset>
</#macro>