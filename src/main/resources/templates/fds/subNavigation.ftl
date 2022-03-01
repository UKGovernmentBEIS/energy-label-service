<#import '/spring.ftl' as spring>

<#function springUrl url>
  <#local springUrl>
    <@spring.url url/>
  </#local>
  <#return springUrl>
</#function>

<#--Sub Navigation Component (left hand navigation)-->
<#macro subNavigation sticky=false>
  <nav class="fds-subnav<#if sticky> fds-subnav--sticky</#if>"<#if sticky> data-module="fds-subnav-sticky"</#if> title="Sub navigation">
    <#nested>
  </nav>
</#macro>

<#macro subNavigationSection themeHeading="">
  <#if themeHeading?has_content>
  <h4 class="fds-subnav__theme">${themeHeading}</h4>
  </#if>
  <ul class="fds-subnav__section">
    <#nested>
  </ul>
</#macro>

<#macro subNavigationSectionItem linkAction linkName currentItemHref>
  <#local linkActionSanitised=linkAction?replace(" ", "%20")>
  <#if currentItemHref?starts_with(linkActionSanitised)>
    <#local currentClass="fds-subnav__section-item--current">
    <#local currentAriaAttr='aria-current="true"'>
  <#else>
    <#local currentClass="">
    <#local currentAriaAttr="">
  </#if>

  <li class="fds-subnav__section-item ${currentClass}" ${currentAriaAttr}>
    <a class="fds-subnav__link govuk-link govuk-link--no-visited-state" href="${linkAction}">${linkName}</a>
    <#if currentItemHref?starts_with(linkActionSanitised) && currentItemHref!="/">
      <#nested>
    </#if>
  </li>
</#macro>

<#macro subNavigationNested>
  <ul class="fds-subnav__section fds-subnav__section--nested">
    <#nested>
  </ul>
</#macro>

<#macro subNavigationNestedLink linkUrl linkText>
  <li class="fds-subnav__section-item">
    <a class="fds-subnav__link govuk-link govuk-link--no-visited-state" href="${linkUrl}">${linkText}</a>
  </li>
</#macro>
