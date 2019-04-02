<#import '/spring.ftl' as spring>
<#--IRS-->
<#import 'form.ftl' as form>
<#--GOVUK-->
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
  >

  <#--Checks if the heading has content in order to not display an empty <h1>-->
  <#local heading=pageHeading?has_content>

<!DOCTYPE html>
<html lang="en" class="govuk-template ">

<head>
  <#if googleAnalyticsEnabled>
    <script src="<@spring.url'/assets/scripts/googleAnalytics.js'/>"></script>
  </#if>

  <meta charset="utf-8" />
  <title><#if errorList?has_content>Error: </#if><#if pageTitle?has_content>${pageTitle} - <#elseif pageHeading?has_content>${pageHeading} - </#if>Create an energy label - GOV.UK</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="theme-color" content="#0b0c0c" />
  <meta name="robots" content="noindex, nofollow" />
  <link rel="shortcut icon" sizes="16x16 32x32 48x48" href="<@spring.url'/assets/govuk-frontend/assets/images/favicon.ico'/>" type="image/x-icon" />
  <link rel="mask-icon" href="<@spring.url'/assets/govuk-frontend/assets/images/govuk-mask-icon.svg'/>" color="#0b0c0c">
  <link rel="apple-touch-icon" sizes="180x180" href="<@spring.url'/assets/govuk-frontend/assets/images/govuk-apple-touch-icon-180x180.png'/>">
  <link rel="apple-touch-icon" sizes="167x167" href="<@spring.url'/assets/govuk-frontend/assets/images/govuk-apple-touch-icon-167x167.png'/>">
  <link rel="apple-touch-icon" sizes="152x152" href="<@spring.url'/assets/govuk-frontend/assets/images/govuk-apple-touch-icon-152x152.png'/>">
  <link rel="apple-touch-icon" href="<@spring.url'/assets/govuk-frontend/assets/images/govuk-apple-touch-icon.png'/>">

  <!--[if !IE 8]><!-->
  <link rel="stylesheet" href="<@spring.url'/assets/static/css/main.css'/>">
  <!--<![endif]-->

  <!--[if IE 8]>
  <link rel="stylesheet" href="<@spring.url'/assets/static/css/main-ie8.css'/>">
  <![endif]-->

  <meta property="og:image" content="<@spring.url'/assets/govuk-frontend/assets/images/govuk-opengraph-image.png'/>">
</head>

<body class="govuk-template__body ">
  <script src="<@spring.url'/assets/scripts/jsCheck.js'/>"></script>

  <a href="#main-content" class="govuk-skip-link">Skip to main content</a>

  <@govukHeader.header currentUserView/>

  <div class="govuk-width-container">

    <#if phaseBanner>
      <div class="govuk-phase-banner">
        <p class="govuk-phase-banner__content">
          <strong class="govuk-tag govuk-phase-banner__content__tag ">beta</strong>
          <span class="govuk-phase-banner__text">This is a new service – you can <a class="govuk-link" href="mailto:energy-label-support@fivium.co.uk">email your feedback</a> to help us improve it.</span>
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
            <#if errorList?has_content>
              <@govukErrorSummary.errorSummary errorItems=errorList/>
            </#if>

            <#if labelMode?has_content && labelMode=='INTERNET'>
              <span class="${captionClass}">Online arrow link</span>
            </#if>
            <#--GOVUK heading class names https://design-system.service.gov.uk/styles/typography/-->
            <#if heading>
              <h1 class="${headingCssClass}">${pageHeading}</h1>
            </#if>

              <#if showInsetText>
                <div class="govuk-inset-text">
                  <#if labelMode?has_content && labelMode=='INTERNET'>
                    <p>
                      Use this form to download a small image that links to the energy label on a website. You should
                      use this link if you can’t fit the label next to the product’s price on the screen. If you need
                      a full label, <a class="govuk-link" href="?mode=ENERGY">create and download an energy label</a>.
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

  <script src="<@spring.url'/assets/govuk-frontend/all.js'/>"></script>
  <script src="<@spring.url'/assets/scripts/frontendInit.js'/>"></script>
</body>

</html>
</#macro>