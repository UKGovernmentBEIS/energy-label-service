<#--GOVUK Error Summary-->
<#--https://design-system.service.gov.uk/components/error-summary/-->
<#macro errorSummary errorItems errorTitle="There is a problem">
  <#if errorItems?has_content>
    <div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="error-summary">
      <h2 class="govuk-error-summary__title" id="error-summary-title">
        ${errorTitle}
      </h2>
      <div class="govuk-error-summary__body">
        <ul class="govuk-list govuk-error-summary__list">
          <#list errorItems as errorField,errorMessage>
            <li>
              <a href="#${errorField}">${errorMessage}</a>
            </li>
          </#list>
        </ul>
      </div>
    </div>
  </#if>
</#macro>