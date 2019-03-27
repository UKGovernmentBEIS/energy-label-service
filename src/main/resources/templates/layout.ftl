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
  >

  <#--Checks if the heading has content in order to not display an empty <h1>-->
  <#local heading=pageHeading?has_content>

<!DOCTYPE html>
<html lang="en" class="govuk-template ">

<head>
  <!-- Google Analytics -->
  <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-136887405-1', 'auto');
    ga('send', 'pageview');
  </script>
  <!-- End Google Analytics -->
  <meta charset="utf-8" />
  <title><#if errorList?has_content>Error: </#if><#if pageTitle?has_content>${pageTitle} - <#elseif pageHeading?has_content>${pageHeading} - </#if>Get an energy label - GOV.UK</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="theme-color" content="#0b0c0c" />
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

  <!--[if lt IE 9]>
  <script src="<@spring.url'/html5-shiv/html5shiv.js'/>"></script>
  <![endif]-->

  <meta property="og:image" content="<@spring.url'/assets/images/govuk-opengraph-image.png'/>">
</head>

<body class="govuk-template__body ">
  <script>
    document.body.className = ((document.body.className) ? document.body.className + ' js-enabled' : 'js-enabled');
  </script>

  <a href="#main-content" class="govuk-skip-link">Skip to main content</a>

  <@govukHeader.header currentUserView/>

  <div class="govuk-width-container">

    <#if phaseBanner>
      <div class="govuk-phase-banner">
        <p class="govuk-phase-banner__content">
          <strong class="govuk-tag govuk-phase-banner__content__tag ">beta</strong>
          <span class="govuk-phase-banner__text">This is a new service – your <a class="govuk-link" href="#">feedback</a> will help us to improve it.</span>
        </p>
      </div>
    </#if>

    <#if breadcrumbMap?has_content>
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
              <span class="${captionClass}">Internet label</span>
            </#if>
            <#--GOVUK heading class names https://design-system.service.gov.uk/styles/typography/-->
            <#if heading>
              <h1 class="${headingCssClass}">${pageHeading}</h1>
            </#if>

              <#if showInsetText>
                <div class="govuk-inset-text">
                  <#if labelMode?has_content && labelMode=='INTERNET'>
                    <p>
                      For a full energy label you can print you can <a class="govuk-link" href="?mode=ENERGY">generate a PDF energy label</a>.
                    </p>
                  <#else>
                    <p>
                      Fill in this form to get an energy label you can print. If the product will be shown on a website, you must also <a class="govuk-link" href="?mode=INTERNET">get an online version of the label</a>.
                    </p>
                  </#if>
                </div>
            </#if>


            <#nested>
          </div>
        <#--<#elseif twoThirdsOneColumn>-->
          <#--<div class="govuk-grid-column-two-thirds">-->
            <#--<h1 class="${headingCssClass}">${pageHeading}</h1>-->
            <#--<#nested>-->
          <#--</div>-->
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
  <script>
    window.GOVUKFrontend.initAll()
  </script>
</body>

</html>
</#macro>