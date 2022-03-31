<#include '../layout.ftl'>
<#include 'apiDocumentationNav.ftl'>

<@defaultPage pageTitle="The energy label API" twoThirdsColumn=false robotsMeta="all">
  <div class="govuk-grid-row">
    <div class="govuk-grid-column-one-quarter">
      <@apiDocumentationNav/>
    </div>
    <div class="govuk-grid-column-three-quarters">
      <h1 class="govuk-heading-xl" id="about-api">
        The energy label API
      </h1>

      <p class="govuk-body">
        The energy label API lets you create energy labels for products sold in Great Britain.
      </p>

      <div class="govuk-inset-text">
        If you want to create a single energy label or you're not a developer, you can
        <a href="${serviceHomePageUrl}" class="govuk-link">use the 'create an energy label' service</a> instead.
      </div>

      <p class="govuk-body">
        You can use the API to create energy labels in your own applications from your product data. You can create
        any of the types of label that are available on this service, as well as efficiency class arrow images for
        websites and other distance selling.
      </p>
      <p class="govuk-body">
        The energy label API is free to use and you don't need to register, but you can
        <a class="govuk-link" href="#">sign up to receive email updates</a>.
      </p>

      <h2 class="govuk-heading-m" id="rate-limits">
        Endpoints
      </h2>
      <p class="govuk-body">
        Use the 'Endpoints' section of the menu <span class="els-non-mobile-only">on the left</span>
        <span class="els-mobile-only">at the top</span> of this page to see what endpoints are available and
        how to use them.
      </p>

      <p class="govuk-body">
        The endpoints are also listed in <a href="${apiSpecUrl}" class="govuk-link" download>the energy label API OpenAPI 3 document</a>.
      </p>

      <h2 class="govuk-heading-m" id="rate-limits">
        Rate limits
      </h2>
      <p class="govuk-body">
        Requests to the energy label API are rate limited to ${rateLimitCapacity} requests every ${rateLimitTimeValue} ${rateLimitTimeUnit} per IP address.
        Each response will include an <code class="els-code els-code--inline">X-Rate-Limit-Remaining</code> header which will tell you how many more requests you can make within that ${rateLimitTimeValue} ${rateLimitTimeUnit} timeframe.
      </p>
      <p class="govuk-body">
        You should store the labels you generate in your application, rather than making repeated requests
        for the same product's label. This will help you avoid being rate limited.
      </p>
      <p class="govuk-body">
        If you exceed the rate limit, you'll receive a response with the <code class="els-code els-code--inline">429 Too Many Requests</code>
        HTTP status code. You can retry your request in ${rateLimitTimeValue} ${rateLimitTimeUnit}.
        The response will include an <code class="els-code els-code--inline">X-Rate-Limit-Retry-After-Seconds</code> header which will tell you how many seconds you have left until you can make another successful request.
      </p>

      <h2 class="govuk-heading-m" id="testing">
        Testing
      </h2>
      <p class="govuk-body">
        The energy label API doesn't require authentication and doesn't store the labels you create, so there is no
        sandbox environment for testing. You can use the live API URL for development, testing and production.
      </p>
      <p class="govuk-body">
        You can import <a href="${apiSpecUrl}" class="govuk-link" download>the energy label API OpenAPI 3 document</a> into API testing tools
        like Postman to test all of our API endpoints.
      </p>

      <h2 class="govuk-heading-m" id="validation">
        Request validation
      </h2>
      <p class="govuk-body">
        These endpoints return data in PDF format for energy labels, PNG or JPEG for arrow images and JSON for errors.
      </p>
      <p class="govuk-body">
        You can import <a href="${apiSpecUrl}" class="govuk-link" download>the energy label API OpenAPI 3 document</a> into API testing tools
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
    {
      "propertyName": "modelName",
      "errorMessage": "Enter a supplier model identification code"
    },
    {
      "propertyName": "supplierName",
      "errorMessage": "Enter a supplier name or trade mark"
    }
  ],
  "path": "/api/v1/path/to/endpoint"
}</code>
      </p>

      <h2 class="govuk-heading-m" id="versioning">
        Versioning
      </h2>
      <p class="govuk-body">
        The current version of the energy label API is ${apiVersion}.
      </p>
      <p class="govuk-body">
        We'll only change the version number of the API if we make changes that might break applications using the API.
        These changes include:
      </p>
      <ul class="govuk-list govuk-list--bullet">
        <li>changing the intended behaviour of any existing endpoints</li>
        <li>removing endpoints</li>
      </ul>
      <p class="govuk-body">
        We will not change the version number of the API for changes that:
      </p>
      <ul class="govuk-list govuk-list--bullet">
        <li>add new endpoints</li>
        <li>fix bugs, unless the fix could break existing applications</li>
      </ul>
      <p class="govuk-body">
        If you <a href="#email-updates" class="govuk-link">sign up to receive emails from us</a> we'll email you to let you know when
        we make changes to the API or release a new version. We'll also add a notice to this page.
      </p>
      <p class="govuk-body">
        After we've released a new version of the API, the previous version will keep working for at least 6 months.
        This will give you time to update your application to use the new version. When we announce a new version, we'll
        tell you when the old version will stop working.
      </p>
      <p class="govuk-body">
        When we release a new version of the API, all of our endpoint paths will change to include the new version number.
        You'll need to update the endpoint URLs your application uses to access the API. You'll need to test that your
        application still works with the new version. You should read the
        <a class="govuk-link" href="#version-history-release-notes">release notes for the current version</a>
        to see what's changed and update your application if necessary.
      </p>

      <h2 class="govuk-heading-m" id="cors">
        Cross-origin requests
      </h2>
      <p class="govuk-body">
        Cross-origin requests are not supported by the API, so you can't make API requests directly from client side JavaScript code in a web browser.
      </p>
      <p class="govuk-body">
        If you want to integrate the API with a web application, please route requests through a back-end service.
      </p>

      <h2 class="govuk-heading-m" id="availability">
        Availability
      </h2>
      <p class="govuk-body">
        We aim to make the energy label API available 24 hours a day, 7 days a week. However, availability of the API is not
        guaranteed and there may be unexpected downtime or performance degradation at any time. Your application should be
        resilient to the energy label API not being available, for example:
      </p>
      <ul class="govuk-list govuk-list--bullet">
        <li>taking a long time to send a response</li>
        <li>timing out before sending a response</li>
        <li>
          returning responses with HTTP status codes other than <code class="els-code els-code--inline">200 OK</code>,
          including 5xx server error status codes
        </li>
      </ul>
      <p class="govuk-body">
        You should store the labels you generate in your application, rather than making repeated requests for the same
        product's label. This will limit the impact of any downtime on your application.
      </p>
      <p class="govuk-body">
        If you <a href="#email-updates" class="govuk-link">sign up to receive emails from us</a> we'll email you before any planned
        downtime, or to update you on any unplanned downtime.
      </p>

      <h2 class="govuk-heading-m" id="support">
        Support
      </h2>
      <p class="govuk-body">
        If you've found a bug or you have issues accessing the energy label API, suggestions for new features or any
        other feedback, please contact
        <a class="govuk-link" href="mailto:efficientproducts@beis.gov.uk?subject=Energy label API feedback">
          efficientproducts@beis.gov.uk
        </a>
      </p>

      <h2 class="govuk-heading-m" id="email-updates">
        Email updates
      </h2>
      <p class="govuk-body">
        You can <a class="govuk-link" href="#">sign up to receive email updates about the energy label API</a>. We'll
        notify you about any downtime and let you know when we make important changes.
      </p>
      <p class="govuk-body">
        You don't have to sign up for email updates to use the API, but we encourage you to sign up if you're using the API
        in production. If you don't, you may not find out about downtime or changes that affect your application.
      </p>

      <h2 class="govuk-heading-m" id="version-history-release-notes">
        Version history and release notes
      </h2>
      <h3 class="govuk-heading-s">${apiVersion}</h3>
      <p class="govuk-body">
        This is the first version of the energy label API.
      </p>
    </div>
  </div>
</@defaultPage>
