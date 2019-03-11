<#import '/spring.ftl' as spring>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Select-->
<#--https://design-system.service.gov.uk/components/select/-->
<#macro select path options label="">
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <label class="govuk-label" for="${id}">
      ${label}
    </label>
    <#if hasError>
      <span id="${id}-error" class="govuk-error-message">
        ${spring.status.errorMessages?join(" ")}
      </span>
    </#if>
    <select class="govuk-select <#if hasError>govuk-select--error</#if>" id="${id}" name="${spring.status.expression}">
      <#if spring.stringStatusValue?has_content>
      <#else>
          <option value="" selected disabled>Select One...</option>
      </#if>
      <#list options?keys as option>
        <#assign isSelected = spring.stringStatusValue == option>
        <option value="${option}" <#if isSelected>selected</#if>>${options[option]}</option>
      </#list>
    </select>
  </div>
</#macro>