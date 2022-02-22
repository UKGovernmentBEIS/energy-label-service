<#import '/spring.ftl' as spring>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Input-->
<#--https://design-system.service.gov.uk/components/text-input/-->
<#macro textInput path label="" inputWidth="govuk-input--width-100" fieldHintOverride="">
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>
  <#local fieldPrompt=(fieldPromptMapping[spring.status.path].promptText)!label>
  <#local fieldHint=fieldHintOverride?has_content?then(fieldHintOverride, (fieldPromptMapping[spring.status.path].hintText)!)>
  <#local fieldWidth=fieldWidthMapping[spring.status.path]!>
  <#local hiddenField=hiddenFields?seq_contains(spring.status.path)!false>
  <#local numericField=(numericFields?seq_contains(spring.status.path))!false>

  <#if !hiddenField>
    <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
      <label class="govuk-label" for="${id}">
        ${fieldPrompt?no_esc}
      </label>
      <#if fieldHint?has_content>
        <div id="${id}-hint" class="govuk-hint">
          ${fieldHint}
        </div>
      </#if>
      <#if hasError>
        <p id="${id}-error" class="govuk-error-message">
        <#list spring.status.errorMessages as errorMessage>
          ${errorMessage}<br/>
        </#list>
        </p>
      </#if>
      <input class="govuk-input <#if fieldWidth?has_content>govuk-input--width-${fieldWidth} </#if> <#if hasError>govuk-input--error </#if>" id="${id}" name="${spring.status.expression}" type="text" <#if numericField>inputmode="numeric" pattern="[0-9]*" </#if> <#if fieldHint?has_content>aria-describedby="${id}-hint" </#if> value="${spring.stringStatusValue}">
    </div>
  </#if>


</#macro>
