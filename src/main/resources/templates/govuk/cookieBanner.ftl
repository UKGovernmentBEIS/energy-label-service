<#macro cookieBanner>
  <div class="govuk-cookie-banner" role="region" aria-label="Cookies on the 'Create an energy label' service" id="els-cookie-banner" hidden>

    <div class="govuk-cookie-banner__message govuk-width-container" id="els-cookie-banner__options">

      <div class="govuk-grid-row">
        <div class="govuk-grid-column-two-thirds">
          <h2 class="govuk-cookie-banner__heading govuk-heading-m">Cookies on the 'Create an energy label' service</h2>

          <div class="govuk-cookie-banner__content">
            <p>We use some essential cookies to make this service work.</p>
            <p>We’d also like to use analytics cookies so we can understand how you use the service and make improvements.</p>
          </div>
        </div>
      </div>

      <div class="govuk-button-group">
        <button value="accept" type="button" name="cookies" class="govuk-button" data-module="govuk-button" id="els-cookie-banner__accept">
          Accept analytics cookies
        </button>
        <button value="reject" type="button" name="cookies" class="govuk-button" data-module="govuk-button" id="els-cookie-banner__reject">
          Reject analytics cookies
        </button>
        <a class="govuk-link" href="/cookies">View cookies</a>
      </div>
    </div>

    <div class="govuk-cookie-banner__message govuk-width-container" role="alert" tabindex="-1" id="els-cookie-banner__accepted-message" hidden>

      <div class="govuk-grid-row">
        <div class="govuk-grid-column-two-thirds">
          <div class="govuk-cookie-banner__content">
            <p>You’ve accepted analytics cookies. You can <a href="/cookies" class="govuk-link">change your cookie settings</a> at any time.</p>
          </div>
        </div>
      </div>

      <div class="govuk-button-group">
        <button class="govuk-button" data-module="govuk-button" id="els-cookie-banner__accepted-hide">
          Hide this message
        </button>
      </div>
    </div>

    <div class="govuk-cookie-banner__message govuk-width-container" role="alert" tabindex="-1" id="els-cookie-banner__rejected-message" hidden>

      <div class="govuk-grid-row">
        <div class="govuk-grid-column-two-thirds">
          <div class="govuk-cookie-banner__content">
            <p>You’ve rejected analytics cookies. You can <a href="/cookies" class="govuk-link">change your cookie settings</a> at any time.</p>
          </div>
        </div>
      </div>

      <div class="govuk-button-group">
        <button class="govuk-button" data-module="govuk-button" id="els-cookie-banner__rejected-hide">
          Hide this message
        </button>
      </div>
    </div>
  </div>
</#macro>
