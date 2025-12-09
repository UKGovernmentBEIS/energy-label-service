<#include '../../layout.ftl'>

<@defaultPage pageTitle="What do you need to create?">
    <@form.govukForm submitUrl>

        <@govukRadios.radio path="form.labelType" radioItems=labelTypes label="What do you need to create?" legendHeadingClass="govuk-fieldset__legend--xl"/>

        <@govukDetails.details summaryTitle="What kind of label should I choose?">
          <p>
            Our <a href="https://www.legislation.gov.uk/eur/2012/392" class="govuk-link" target="_blank">current legislation (opens in a new tab)</a>
            still requires use of the 2012 label for all household tumble dryers placed on the GB market. Our
            <a href="<@spring.url'/assets/files/tumble-dryer-easement-notice.pdf'/>" class="govuk-link" target="_blank">easement (PDF, opens in a new tab)</a>,
            however, confirms that we will not take enforcement action if you choose to use the easement label instead.
          </p>
          <p>
            The product's energy efficiency class on the easement label will be lower than its class on the 2012 label.
            Before you create an easement label, you need to make sure the values you're entering are based on the
            criteria for the rescaled label. You shouldn't copy values directly from the 2012 label.
          </p>
          <p>
            Our easement will remain in place until the current GB tumble dryer labelling regime is replaced, 10 months
            after we lay our awaited Statutory Instrument (SI). From this “replacement date”, all products which are
            newly placed on the market must use the new label required by our new SI (which will be made available on
            this service in due course). Products placed on the market before the “replacement date” can continue to
            use the 2012 label or benefit from our easement.
          </p>
          <p>
            Further information is provided in the
            <a href="<@spring.url'/assets/files/tumble-dryer-easement-notice.pdf'/>" class="govuk-link" target="_blank">enforcement easement note (PDF, opens in a new tab)</a>.
          </p>
        </@govukDetails.details>

        <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
    </@form.govukForm>

</@defaultPage>