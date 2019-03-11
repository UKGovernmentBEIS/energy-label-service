<#import '/spring.ftl' as spring>
<#import 'fieldset.ftl' as checkboxFieldset>

<#--GOVUK Checkboxes-->
<#--https://design-system.service.gov.uk/components/checkboxes/-->
<#macro checkboxes path checkBoxes label>
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>
  <#local questionText=questionMapping[spring.status.path].questionText!label>
  <#local primaryHintText=questionMapping[spring.status.path].primaryHintText!"">
  <#local furtherHintTitle=questionMapping[spring.status.path].furtherGuidanceTitle!"">
  <#local furtherHintText=questionMapping[spring.status.path].furtherGuidanceText!"">

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
  <@checkboxFieldset.fieldset legendHeading=questionText legendHeadingClass="govuk-fieldset__legend--m" mandatory=mandatory>
    <#if primaryHintText?has_content>
      <span id="${id}-hint" class="govuk-hint">
        ${primaryHintText}
      </span>
    </#if>
    <#if furtherHintTitle?has_content>
      <@furtherGuidance.details summaryTitle=furtherHintTitle summaryText=furtherHintText/>
    </#if>
    <#if hasError>
      <span id="${id}-error" class="govuk-error-message">
        ${spring.status.errorMessages?join(" ")}
      </span>
    </#if>
    <div class="govuk-checkboxes">
    <#list checkBoxes as check>
      <div class="govuk-checkboxes__item">
      <input class="govuk-checkboxes__input" id="${id}" name="${spring.status.expression}" type="checkbox" value="${check}">
        <label class="govuk-label govuk-checkboxes__label" for="${id}">
          ${checkBoxes[check]}
        </label>
      </div>
    </#list>
    </div>
  </@checkboxFieldset.fieldset>
  </div>
</#macro>

<#--GOVUK Checkboxes-->
<#--https://design-system.service.gov.uk/components/checkboxes/-->
<#macro govukCheckBoxesMap path checkBoxes label>
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>
  <#local questionText=questionMapping[spring.status.path].questionText!label>
  <#local primaryHintText=questionMapping[spring.status.path].primaryHintText!"">
  <#local furtherHintTitle=questionMapping[spring.status.path].furtherGuidanceTitle!"">
  <#local furtherHintText=questionMapping[spring.status.path].furtherGuidanceText!"">

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
  <@checkboxFieldset.fieldset legendHeading="${label}">
    <#if primaryHintText?has_content>
      <span id="${id}-hint" class="govuk-hint">
        ${primaryHintText}
      </span>
    </#if>
    <#if furtherHintTitle?has_content>
      <@furtherGuidance.details summaryTitle=furtherHintTitle summaryText=furtherHintText/>
    </#if>
    <#if hasError>
      <span id="${id}-error" class="govuk-error-message">
      ${spring.status.errorMessages?join(" ")}
      </span>
    </#if>
    <div class="govuk-checkboxes">
    <#list checkBoxes?keys as check>
      <div class="govuk-checkboxes__item">
      <#assign isSelected = checkBoxes[check].isRoleAssigned()>
        <input class="govuk-checkboxes__input" id="${id}-${check}" name="${spring.status.expression}" type="checkbox" value="${check}" <#if isSelected> checked="checked"</#if>>
        <label class="govuk-label govuk-checkboxes__label" for="${id}-${check}">
          ${checkBoxes[check].getTeamRoleDescription()}
        </label>
      </div>
    </#list>
    </div>
  </@checkboxFieldset.fieldset>
  </div>
</#macro>