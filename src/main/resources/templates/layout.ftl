<#import '/spring.ftl' as spring>
<#--IRS-->
<#import 'form.ftl' as form>
<#--GOVUK-->
<#import 'govuk/cookieBanner.ftl' as govukCookieBanner>
<#import 'govuk/header.ftl' as govukHeader>
<#import 'govuk/footer.ftl' as govukFooter>
<#import 'govuk/breadcrumbs.ftl' as govukBreadcrumbs>
<#import 'govuk/button.ftl' as govukButton>
<#import 'govuk/details.ftl' as govukDetails>
<#import 'govuk/errorSummary.ftl' as govukErrorSummary>
<#import 'govuk/fieldset.ftl' as govukFieldset>
<#import 'govuk/panel.ftl' as govukPanel>
<#import 'govuk/radio.ftl' as govukRadios>
<#import 'govuk/select.ftl' as govukSelect>
<#import 'govuk/textInput.ftl' as govukTextInput>

<#import 'common.ftl' as common>

<#function springUrl url>
  <#local springUrl>
    <@spring.url url/>
  </#local>
  <#return springUrl>
</#function>

<#macro blankMacro></#macro>

<#macro defaultPage
  pageHeading=""
  caption=""
  captionClass="govuk-caption-xl"
  twoThirdsColumn=true
  headingCssClass="govuk-heading-xl"
  backLink=false
  phaseBanner=true
  pageTitle=""
  showInsetText=false
  showBreadcrumbs=true
  pageScripts=blankMacro
  notificationBanner=blankMacro
  beforeStandardInsetText=""
  robotsMeta="noindex, nofollow"
  >

  <#--Checks if the heading has content in order to not display an empty <h1>-->
  <#local heading=pageHeading?has_content>

<!DOCTYPE html>
<html lang="en" class="govuk-template govuk-template--rebranded">

<head>

  <meta charset="utf-8" />
  <title><#if errorList?has_content>Error: </#if><#if pageTitle?has_content>${pageTitle} - <#elseif pageHeading?has_content>${pageHeading} - </#if>Create an energy label - GOV.UK</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="theme-color" content="#0b0c0c" />
  <meta name="robots" content="${robotsMeta}" />
  <link rel="icon" sizes="48x48" href="<@spring.url'/assets/govuk-frontend/dist/govuk/assets/rebrand/images/favicon.ico'/>" type="image/x-icon">
  <link rel="icon" sizes="any" href="<@spring.url'/assets/govuk-frontend/dist/govuk/assets/rebrand/images/favicon.svg'/>" type="image/svg+xml">
  <link rel="mask-icon" href="<@spring.url'/assets/govuk-frontend/dist/govuk/assets/rebrand/images/govuk-icon-mask.svg'/>" color="#0b0c0c">
  <link rel="apple-touch-icon" sizes="180x180" href="<@spring.url'/assets/govuk-frontend/dist/govuk/assets/rebrand/images/govuk-icon-180.png'/>">
  <link rel="manifest" href="<@spring.url'/assets/govuk-frontend/dist/govuk/assets/rebrand/manifest.json'/>">

  <link rel="stylesheet" href="<@spring.url'/assets/static/css/main.css'/>">

  <meta property="og:image" content="<@spring.url'/assets/govuk-frontend/dist/govuk/assets/rebrand/images/govuk-opengraph-image.png'/>">
</head>

<body class="govuk-template__body ">
  <script src="<@spring.url'/assets/scripts/jsCheck.js'/>"></script>

  <@govukCookieBanner.cookieBanner/>

  <a href="#main-content" class="govuk-skip-link" data-module="govuk-skip-link">Skip to main content</a>

  <@govukHeader.header currentUserView/>

  <div class="govuk-width-container">

    <#if phaseBanner>
      <div class="govuk-phase-banner">
        <p class="govuk-phase-banner__content">
          <strong class="govuk-tag govuk-phase-banner__content__tag ">Beta</strong>
          <span class="govuk-phase-banner__text">This is a new service – you can <a class="govuk-link" href="mailto:efficientproducts@energysecurity.gov.uk">email your feedback</a> to help us improve it.</span>
        </p>
      </div>
    </#if>

    <#if showBreadcrumbs && breadcrumbMap?has_content>
      <@govukBreadcrumbs.breadcrumbs breadcrumbMap/>
    </#if>

    <#if backLink>
      <a href="#" class="govuk-back-link">Back</a>
    </#if>

    <main class="govuk-main-wrapper " id="main-content" role="main">
      <#if twoThirdsColumn>
        <div class="govuk-grid-row">
          <div class="govuk-grid-column-two-thirds">
            <@notificationBanner/>
            <#if errorList?has_content>
              <@govukErrorSummary.errorSummary errorItems=errorList/>
            </#if>

            <#if labelMode?has_content && labelMode=='INTERNET'>
              <span class="${captionClass}">
              <#if showRescaledInternetLabelGuidance>
                  Arrow image
                <#else>
                  Online arrow link
              </#if>
              </span>
            </#if>

            <#if caption?has_content>
              <span class="${captionClass}">
                ${caption}
              </span>
            </#if>

            <#--GOVUK heading class names https://design-system.service.gov.uk/styles/typography/-->
            <#if heading>
              <h1 class="${headingCssClass}">${pageHeading}</h1>
            </#if>

              <#if showInsetText>
                <div class="govuk-inset-text">
                  <#if beforeStandardInsetText?has_content>
                    <p>
                      ${beforeStandardInsetText}
                    </p>
                  </#if>
                  <#if labelMode?has_content && labelMode=='INTERNET'>
                    <#if showRescaledInternetLabelGuidance>
                      <p>
                        Use this form to download an arrow image of this product’s energy efficiency class. This image should be used:
                      </p>
                      <ul>
                        <li>on the internet, to link to the full label if you can’t fit the label next to the product's price on screen</li>
                        <li>
                          in visual advertisements, promotional material and paper-based distance selling materials,
                          if the product's energy label is a new-style 'rescaled' label
                        </li>
                      </ul>
                      <p>
                        If you need a full label,
                        <a class="govuk-link" href="?mode=ENERGY">create and download an energy label</a>.
                      </p>
                    <#else>
                      <p>
                        Use this form to download a small image that links to the energy label on a website. You should
                        use this link if you can’t fit the label next to the product’s price on the screen. If you need
                        a full label, <a class="govuk-link" href="?mode=ENERGY">create and download an energy label</a>.
                      </p>
                    </#if>
                  <#elseif showRescaledInternetLabelGuidance>
                    <p>
                      You can also <a class="govuk-link" href="?mode=INTERNET">download an arrow image</a> showing this
                      product’s energy efficiency class. You might need to show this image in advertisements,
                      promotional material, or on the internet.
                    </p>
                  <#else>
                    <p>
                      If you sell the product online you might also need to
                      <a class="govuk-link" href="?mode=INTERNET">download an arrow image</a> to link to the label.
                    </p>
                  </#if>
                </div>
            </#if>


            <#nested>
          </div>
        </div>
        <#else>
        <#if heading>
          <h1 class="${headingCssClass}">${pageHeading}</h1>
        </#if>
        <#nested>
      </#if>
    </main>
  </div>

  <@govukFooter.footer/>

  <script type="module" src="<@spring.url'/assets/govuk-frontend/dist/govuk/govuk-frontend.min.js'/>"></script>
  <script id="els-init-script" data-govuk-js-url="<@spring.url'/assets/govuk-frontend/dist/govuk/govuk-frontend.min.js'/>" type="module" src="<@spring.url'/assets/scripts/frontendInit.js'/>"></script>
  <#if googleAnalyticsEnabled>
    <script src="<@spring.url'/assets/scripts/googleAnalytics/analyticsEnabled.js'/>"></script>
  <#else>
    <script src="<@spring.url'/assets/scripts/googleAnalytics/analyticsDisabled.js'/>"></script>
  </#if>
  <script src="<@spring.url'/assets/scripts/cookieControl.js'/>"></script>
  <@pageScripts/>
</body>

</html>
</#macro>
