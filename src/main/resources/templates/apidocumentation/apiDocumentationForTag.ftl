<#include '../layout.ftl'>

<@defaultPage pageHeading="${tagName}">
  <#list operationList as operation>
    <@govukDetails.details summaryTitle="${operation.getSummary()}">
      ${operation.getDescription()!operation.getSummary()}
    </@govukDetails.details>
  </#list>
</@defaultPage>