<#import '/spring.ftl' as spring>
<#import 'fieldset.ftl' as dateFieldset>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Date Input-->
<#--https://design-system.service.gov.uk/components/date-input/-->
<#macro dateInput path label="">
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>
  <#local questionText=questionMapping[spring.status.path].questionText!label>
  <#local primaryHintText=questionMapping[spring.status.path].primaryHintText!"">
  <#local furtherHintTitle=questionMapping[spring.status.path].furtherGuidanceTitle!"">
  <#local furtherHintText=questionMapping[spring.status.path].furtherGuidanceText!"">

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <@dateFieldset.fieldset legendHeading=questionText>
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
      <div class="govuk-date-input" id="dob">
        <div class="govuk-date-input__item">
          <div class="govuk-form-group">
            <label class="govuk-label govuk-date-input__label" for="${id}-day">
              Day
            </label>
            <input class="govuk-input <#if hasError>govuk-input--error</#if> govuk-date-input__input govuk-input--width-2" id="${id}-day" name="${id}-day" type="number" pattern="[0-9]*">
          </div>
        </div>
        <div class="govuk-date-input__item">
          <div class="govuk-form-group">
            <label class="govuk-label govuk-date-input__label" for="${id}-month">
              Month
            </label>
            <input class="govuk-input <#if hasError>govuk-input--error</#if> govuk-date-input__input govuk-input--width-2" id="${id}-month" name="${id}-month" type="number" pattern="[0-9]*">
          </div>
        </div>
        <div class="govuk-date-input__item">
          <div class="govuk-form-group">
            <label class="govuk-label govuk-date-input__label" for="${id}-year">
              Year
            </label>
            <input class="govuk-input <#if hasError>govuk-input--error</#if> govuk-date-input__input govuk-input--width-4" id="${id}-year" name="${id}-year" type="number" pattern="[0-9]*">
          </div>
        </div>
      </div>
    </@dateFieldset.fieldset>
  </div>
</#macro>