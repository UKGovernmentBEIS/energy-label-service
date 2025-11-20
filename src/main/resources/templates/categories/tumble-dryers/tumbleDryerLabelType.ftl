<#include '../../layout.ftl'>

<@defaultPage pageTitle="What do you need to create?">
    <@form.govukForm submitUrl>

        <@govukRadios.radio path="form.labelType" radioItems=labelTypes label="What do you need to create?" legendHeadingClass="govuk-fieldset__legend--xl"/>

        <@govukDetails.details summaryTitle="What kind of label should I choose?">
          <p>You can use a new-style rescaled energy label now if the product has been assessed against the criteria for the rescaled label.</p>
          <p>The product's energy efficiency class on the rescaled label will be lower than its class on the old label, 
            and other values might also be calculated differently. Before you create a rescaled label, you need to make sure the values you're entering are based on the criteria for the rescaled label. 
            You shouldn't copy values directly from the old label.</p>
          <#-- TODO set dates -->
          <p>You can continue to use the old-style label for all products until [end date]. 
            From [end date + 1 day], all products which are newly placed on the market must use the new rescaled label. 
            Products placed on the market before [end date] can continue to use the old-style label until [cutoff date].</p>
        </@govukDetails.details>

        <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
    </@form.govukForm>

</@defaultPage>