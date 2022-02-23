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
      <p class="govuk-body">
        These endpoints return data in PDF format for energy labels, PNG or JPEG for arrow images and JSON for errors.
      </p>
      <p class="govuk-body">
        You can import <a href="${apiSpecUrl}" class="govuk-link">the energy label API OpenAPI 3 document</a> into API testing tools
        like Postman to test all of our API endpoints.
      </p>
      <p class="govuk-body">
        The values you send will be validated. If there are any validation errors, the response body will be a JSON object
        containing a <code class="els-code els-code--inline">validationErrors</code> array. For example:
      </p>
      <p class="govuk-body">
        <code class="els-code els-code--block">{
    "timestamp": "2022-02-18T15:28:11.877Z",
    "status": 400,
    "error": "Bad request",
    "message": "There were validation errors",
    "validationErrors": [
      "Field 'modelName' has error: Enter a supplier model identification code",
      "Field 'supplierName' has error: Enter a supplier name or trade mark"
    ],
    "path": "/api/v1/path/to/endpoint"
  }</code>
      </p>

      <h2 class="govuk-heading-l">Endpoints</h2>

      <div class="govuk-inset-text">
        The base URL for all paths is <code class="els-code els-code--inline">${baseUrl}</code>.
        No authentication is required for any endpoints.
      </div>

      <@govukAccordion.accordion accordionId="elg-api-operations">
          <#list operationListWithPath as path, operationWithSchema>
                <@govukAccordion.accordionSection sectionHeading="${operationWithSchema.getOperation().getSummary()}" sectionHeadingSize="h3">
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

                  <table class="govuk-table">
                    <thead class="govuk-table__head">
                    <tr class="govuk-table__row">
                      <th scope="col" class="govuk-table__header">Property</th>
                      <th scope="col" class="govuk-table__header">Description</th>
                      <th scope="col" class="govuk-table__header">Type</th>
                    </tr>
                    </thead>
                    <tbody class="govuk-table__body">
                    <#list operationWithSchema.getSchema().getProperties() as propertyName, schema>
                      <tr class="govuk-table__row">
                        <td class="govuk-table__cell">
                          <code class="els-code els-code--inline">${propertyName}</code>
                        </td>
                        <td class="govuk-table__cell">
                          ${schema.getDescription()}
                          <#if schema.getEnum()?has_content>
                            <br/>
                            Must be one of:<br/>
                            <#list schema.getEnum() as enum>
                              <code class="els-code els-code--inline">${enum}</code><br/>
                            </#list>
                          </#if>
                        </td>
                        <td class="govuk-table__cell">
                          ${schema.getType()?cap_first}
                        </td>
                      </tr>
                    </#list>

<#--                    <tr class="govuk-table__row">-->
<#--                      <td class="govuk-table__cell">-->
<#--                        <code class="els-code els-code--inline">modelName</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Supplier's model identification code-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        String-->
<#--                      </td>-->
<#--                    </tr>-->
<#--                    <tr class="govuk-table__row">-->
<#--                      <td class="govuk-table__cell">-->
<#--                        <code class="els-code els-code--inline">efficiencyRating</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        The energy efficiency class of the cavity. Must be one of-->
<#--                        <code class="els-code els-code--inline">A+++</code>,-->
<#--                        <code class="els-code els-code--inline">A++</code>,-->
<#--                        <code class="els-code els-code--inline">A+</code>,-->
<#--                        <code class="els-code els-code--inline">A</code>,-->
<#--                        <code class="els-code els-code--inline">B</code>,-->
<#--                        <code class="els-code els-code--inline">C</code>,-->
<#--                        <code class="els-code els-code--inline">D</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        String-->
<#--                      </td>-->
<#--                    </tr>-->
<#--                    <tr class="govuk-table__row">-->
<#--                      <td class="govuk-table__cell">-->
<#--                        <code class="els-code els-code--inline">volume</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Usable volume of the cavity in litres (L). This may be up to 3 digit(s) long.-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Integer-->
<#--                      </td>-->
<#--                    </tr>-->
<#--                    <tr class="govuk-table__row">-->
<#--                      <td class="govuk-table__cell">-->
<#--                        <code class="els-code els-code--inline">conventionalKwhConsumption</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Energy consumption of the conventional heating function per cycle, in kWh/cycle. This may be up to 1 digit(s) long with an optional 2 decimal places.-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Number-->
<#--                      </td>-->
<#--                    </tr>-->
<#--                    <tr class="govuk-table__row">-->
<#--                      <td class="govuk-table__cell">-->
<#--                        <code class="els-code els-code--inline">isFanOven</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Is this a fan-forced oven?-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Boolean-->
<#--                      </td>-->
<#--                    </tr>-->
<#--                    <tr class="govuk-table__row">-->
<#--                      <td class="govuk-table__cell">-->
<#--                        <code class="els-code els-code--inline">convectionKwhConsumption</code>-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Energy consumption of the fan-forced heating function per cycle, in kWh/cycle. This may be up to 1 digit(s) long with an optional 2 decimal places.-->
<#--                      </td>-->
<#--                      <td class="govuk-table__cell">-->
<#--                        Number-->
<#--                      </td>-->
<#--                    </tr>-->
                    </tbody>
                  </table>

                </@govukAccordion.accordionSection>
            </#list>

      </@govukAccordion.accordion>
    </div>
  </div>
</@defaultPage>
