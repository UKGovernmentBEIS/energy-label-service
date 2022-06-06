<#import '../fds/subNavigation.ftl' as fdsSubNavigation>

<#macro apiDocumentationNav>
  <@fdsSubNavigation.subNavigation>
    <@fdsSubNavigation.subNavigationSection>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation" linkName="About the API" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#endpoints" linkName="Endpoints" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#rate-limits" linkName="Rate limits" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#testing" linkName="Testing" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#validation" linkName="Request validation" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#versioning" linkName="Versioning" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#cors" linkName="Cross-origin requests" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#availability" linkName="Availability" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#support" linkName="Support" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#email-updates" linkName="Email updates" currentItemHref="dummy"/>
      <@fdsSubNavigation.subNavigationSectionItem linkAction="/api-documentation#version-history-release-notes" linkName="Version history" currentItemHref="dummy"/>
    </@fdsSubNavigation.subNavigationSection>
    <@fdsSubNavigation.subNavigationSection themeHeading="Endpoints">
      <#list tagLinks as tagLink>
        <@fdsSubNavigation.subNavigationSectionItem linkAction=tagLink.url linkName=tagLink.name currentItemHref=currentUrl/>
      </#list>
    </@fdsSubNavigation.subNavigationSection>
  </@fdsSubNavigation.subNavigation>
</#macro>
