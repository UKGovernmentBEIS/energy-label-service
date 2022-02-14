<#include '../layout.ftl'>

<@defaultPage pageHeading="API documentation">
  <ul class="govuk-list govuk-list--bullet">
    <#list tagLinks as tagLink>
        <li><a class="govuk-link" href="${tagLink.url}">${tagLink.name}</a></li>
    </#list>
  </ul>
</@defaultPage>