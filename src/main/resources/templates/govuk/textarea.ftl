<#import '/spring.ftl' as spring>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Textarea-->
<#--https://design-system.service.gov.uk/components/textarea/-->
<#macro textarea path label="" rows=5>
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>
  <#local questionText=questionMapping[spring.status.path].questionText!label>
  <#local primaryHintText=questionMapping[spring.status.path].primaryHintText!"">
  <#local furtherHintTitle=questionMapping[spring.status.path].furtherGuidanceTitle!"">
  <#local furtherHintText=questionMapping[spring.status.path].furtherGuidanceText!"">

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <label class="govuk-label" for="${id}">
      ${questionText} <#if !mandatory>(optional)</#if>
    </label>
    <#if primaryHintText?has_content>
      <span id="${id}-hint" class="govuk-hint">${primaryHintText}</span>
    </#if>
    <#if furtherHintTitle?has_content>
      <@furtherGuidance.details summaryTitle=furtherHintTitle summaryText=furtherHintText/>
    </#if>
    <#if hasError>
      <span id="${id}-error">
        ${spring.status.errorMessages?join(" ")}
      </span>
    </#if>
    <textarea class="govuk-textarea <#if hasError>govuk-textarea--error</#if>" id="${id}" name="${spring.status.expression}" rows=${rows} <#if primaryHintText?has_content>aria-describedby="${id}-hint"</#if>></textarea>
  </div>
</#macro>