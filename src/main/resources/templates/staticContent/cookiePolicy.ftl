<#include '../layout.ftl'>

<#macro cookiePolicyScript>
  <script src="<@spring.url'/assets/scripts/cookiePolicy.js'/>"></script>
</#macro>

<#macro notificationBanner>
  <div class="govuk-notification-banner govuk-notification-banner--success" id="els-cookie-policy__notification-banner" role="alert" aria-labelledby="govuk-notification-banner-title" data-module="govuk-notification-banner" data-disable-auto-focus="true" tabindex="-1" hidden>
    <div class="govuk-notification-banner__header">
      <h2 class="govuk-notification-banner__title" id="govuk-notification-banner-title">
        Success
      </h2>
    </div>
    <div class="govuk-notification-banner__content">
      <p class="govuk-notification-banner__heading">
        You’ve set your cookie preferences.
        <#if referer?has_content>
          <a class="govuk-notification-banner__link" href="${referer}">Go back to the page you were looking at</a>.
          <#else>
          <a class="govuk-notification-banner__link" href="/">Go to the first page of this service</a>.
        </#if>
      </p>
    </div>
  </div>
</#macro>

<@defaultPage pageHeading="Cookies" pageScripts=cookiePolicyScript notificationBanner=notificationBanner>
  <p class="govuk-body">
    Cookies are small files saved on your phone, tablet or computer when you visit a website.
  </p>

  <p class="govuk-body">
    We use cookies to make the 'Create an energy label' service work and collect information about how you use it.
  </p>

  <h2 class="govuk-heading-m">Essential cookies</h2>

  <p class="govuk-body">
    Essential cookies store the choices you make about cookies on this service. We do not need to ask permission to use them.
  </p>

  <table class="govuk-table">
    <caption class="govuk-visually-hidden">essential cookies</caption>
    <thead class="govuk-table__head">
      <tr class="govuk-table__row">
        <th class="govuk-table__header">Name</th>
        <th class="govuk-table__header">Purpose</th>
        <th class="govuk-table__header">Expires</th>
      </tr>
    </thead>
    <tbody class="govuk-table__body">
      <tr class="govuk-table__row">
        <td class="govuk-table__cell">
          els-cookies-allowed
        </td>
        <td width="50%" class="govuk-table__cell">
          Saves your cookie consent settings
        </td>
        <td class="govuk-table__cell">
          1 year
        </td>
      </tr>
    </tbody>
  </table>

  <h2 class="govuk-heading-m">Analytics cookies (optional)</h2>

  <p class="govuk-body">
    With your permission, we use Google Analytics to collect data about how you use the 'Create an energy label' service.
    This information helps us to improve our service.
  </p>

  <p class="govuk-body">
    Google is not allowed to use or share our analytics data with anyone.
  </p>

  <p class="govuk-body">
    Google Analytics stores anonymised information about:
  </p>

  <ul class="govuk-list govuk-list--bullet">
    <li>how you got to the 'Create an energy label' service</li>
    <li>the pages you visit on this service and how long you spend on them</li>
  </ul>

  <table class="govuk-table">
    <caption class="govuk-visually-hidden">Google Analytics cookies</caption>
    <thead class="govuk-table__head">
      <tr class="govuk-table__row">
        <th class="govuk-table__header">Name</th>
        <th class="govuk-table__header">Purpose</th>
        <th class="govuk-table__header">Expires</th>
      </tr>
    </thead>
    <tbody class="govuk-table__body">
    <tr class="govuk-table__row">
      <td class="govuk-table__cell">
        _ga
      </td>
      <td width="50%" class="govuk-table__cell">
        Checks if you’ve visited this service before. This helps us count how many people use the service.
      </td>
      <td class="govuk-table__cell">
        2 years
      </td>
    </tr>
    <tr class="govuk-table__row">
      <td class="govuk-table__cell">
        _gid
      </td>
      <td width="50%" class="govuk-table__cell">
        Checks if you’ve visited this service before. This helps us count how many people use the service.
      </td>
      <td class="govuk-table__cell">
        24 hours
      </td>
    </tr>
    <tr class="govuk-table__row">
      <td class="govuk-table__cell">
        _gat
      </td>
      <td width="50%" class="govuk-table__cell">
        Limits how often data is sent to Google Analytics
      </td>
      <td class="govuk-table__cell">
        1 minute
      </td>
    </tr>
    <tr class="govuk-table__row">
      <td class="govuk-table__cell">
        _gat_govuk_shared
      </td>
      <td width="50%" class="govuk-table__cell">
        Limits how often data is sent to Google Analytics
      </td>
      <td class="govuk-table__cell">
        1 minute
      </td>
    </tr>
    </tbody>
  </table>

  <h2 class="govuk-heading-l">Change your cookie settings</h2>

  <div id="els-cookie-policy__settings" hidden>
    <div class="govuk-form-group">
      <fieldset class="govuk-fieldset">
        <legend class="govuk-fieldset__legend govuk-fieldset__legend--s">
          Do you want to accept analytics cookies?
        </legend>
        <div class="govuk-radios">
          <div class="govuk-radios__item">
            <input class="govuk-radios__input" id="analytics-cookies-yes" name="analytics-cookies" type="radio" value="yes">
            <label class="govuk-label govuk-radios__label" for="analytics-cookies-yes">
              Yes
            </label>
          </div>
          <div class="govuk-radios__item">
            <input class="govuk-radios__input" id="analytics-cookies-no" name="analytics-cookies" type="radio" value="no">
            <label class="govuk-label govuk-radios__label" for="analytics-cookies-no">
              No
            </label>
          </div>
        </div>

      </fieldset>
    </div>

    <button class="govuk-button" data-module="govuk-button" id="els-cookie-policy__settings-save-button">
      Save cookie settings
    </button>
  </div>

  <div id="els-cookie-policy__settings--js-disabled">
    <p class="govuk-body">We cannot change your cookie settings at the moment because JavaScript is not running in your browser. To fix this, try:</p>
    <ul class="govuk-list govuk-list--bullet">
      <li>turning on JavaScript in your browser settings</li>
      <li>reloading this page</li>
    </ul>
  </div>
</@defaultPage>
