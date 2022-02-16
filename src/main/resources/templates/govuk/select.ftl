<#import '/spring.ftl' as spring>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Select-->
<#--https://design-system.service.gov.uk/components/select/-->
<#macro select path options label="">
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local fieldPrompt=(fieldPromptMapping[spring.status.path].promptText)!label>
  <#local fieldHint=(fieldPromptMapping[spring.status.path].hintText)!>
  <#local hiddenField=hiddenFields?seq_contains(spring.status.path)!false>

  <#if !hiddenField>
    <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
      <label class="govuk-label" for="${id}">
        ${fieldPrompt}
      </label>
      <#if fieldHint?has_content>
        <div id="${id}-hint" class="govuk-hint">
          ${fieldHint}
        </div>
      </#if>
      <#if hasError>
        <p id="${id}-error" class="govuk-error-message">
          ${spring.status.errorMessages?join(" ")}
        </p>
      </#if>
      <select class="govuk-select <#if hasError>govuk-select--error</#if>" id="${id}" <#if fieldHint?has_content>aria-describedby="${id}-hint" </#if> name="${spring.status.expression}">
        <#if spring.stringStatusValue?has_content>
        <#else>
            <option value="" selected disabled></option>
        </#if>
        <#list options?keys as option>
          <#assign isSelected = spring.stringStatusValue == option>
          <option value="${option}" <#if isSelected>selected</#if>>${options[option]}</option>
        </#list>
      </select>
    </div>
  </#if>
</#macro>
