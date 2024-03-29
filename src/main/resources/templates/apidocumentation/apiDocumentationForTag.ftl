<#include '../layout.ftl'>
<#include 'apiDocumentationNav.ftl'>
<#import '../govuk/accordion.ftl' as govukAccordion>

<@defaultPage pageHeading="" twoThirdsColumn=false robotsMeta="all">
  <div class="govuk-grid-row">
    <div class="govuk-grid-column-one-quarter">
      <@apiDocumentationNav/>
    </div>
    <div class="govuk-grid-column-three-quarters">
      <span class="govuk-caption-xl">API endpoints</span>
      <h1 class="govuk-heading-xl">${tagName}</h1>

      <div class="govuk-inset-text">
        The base URL for all paths is <code class="els-code els-code--inline">${baseUrl}</code>.
        No authentication is required for any endpoints.
      </div>

      <#assign tagNameSanitised=tagName?replace(" ", "-")>
      <@govukAccordion.accordion accordionId="elg-api-operations-${tagNameSanitised}">
          <#list operationListWithPath as path, operationWithSchema>
                <@govukAccordion.accordionSection sectionHeading="${operationWithSchema.getOperation().getSummary()}" sectionHeadingSize="h2">
                    <#if operationWithSchema.getOperation().getDescription()?has_content>
                      <p class="govuk-body">
                          ${operationWithSchema.getOperation().getDescription()}
                      </p>
                    </#if>

                  <h3 class="govuk-heading-m">Path</h3>

                  <p class="govuk-body">
                    <code class="els-code els-code--inline">${path}</code>
                  </p>

                  <h3 class="govuk-heading-m">Body schema</h3>

                  <p class="govuk-body">
                    Send a <code class="els-code els-code--inline">POST</code> request to the path above. The body of the request must be a JSON object with the following properties:
                  </p>

                  <table class="govuk-table els-api-schema-table">
                    <thead class="govuk-table__head">
                    <tr class="govuk-table__row">
                      <th scope="col" class="govuk-table__header els-api-schema-table__property-header">Property</th>
                      <th scope="col" class="govuk-table__header els-api-schema-table__description-header">Description</th>
                      <th scope="col" class="govuk-table__header els-api-schema-table__type-header">Type</th>
                    </tr>
                    </thead>
                    <tbody class="govuk-table__body">
                      <#list operationWithSchema.getSchema().getProperties() as propertyName, schema>
                        <tr class="govuk-table__row">
                          <td class="govuk-table__cell els-api-schema-table__property-cell">
                            <code>${propertyName}</code>
                          </td>
                          <td class="govuk-table__cell">
                            ${schema.getDescription()?no_esc}
                            <#if schema.getEnum()?has_content>
                              <br/><br/>
                              Must be one of:<br/>
                              <#list schema.getEnum() as enum>
                                <code>${enum}</code><br/>
                              </#list>
                            </#if>
                          </td>
                          <td class="govuk-table__cell">
                            ${schema.getType()?cap_first}
                          </td>
                        </tr>
                      </#list>
                    </tbody>
                  </table>

                  <h3 class="govuk-heading-m">Example request body</h3>

                  <p class="govuk-body">
                    <code class="els-code els-code--block">${operationWithSchema.getExample()}</code>
                  </p>
                </@govukAccordion.accordionSection>
            </#list>
      </@govukAccordion.accordion>
    </div>
  </div>
</@defaultPage>
