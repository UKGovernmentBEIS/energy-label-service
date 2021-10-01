<#include '../../layout.ftl'>

<@common.standardProductForm "Lamps and light sources">
  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2" isAboveDetailsComponent=true>
    <@common.postSeptember2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.qrCodeUrl" fieldHintOverride="This link will be shown as a QR code on the label. Links should be under 100 characters to make sure they can be scanned reliably. "/>
      <@govukRadios.radio path="form.templateSize" radioItems=templateSize legendSize="h2"/>
      <@govukRadios.radio path="form.templateColour" radioItems=templateColour legendSize="h2"/>
    </@common.postSeptember2021RadioItem>
    <@common.preSeptember2021RadioItem legislationCategories/>
  </@govukRadios.radioGroup>

  <@govukDetails.details summaryTitle="What kind of label should I choose?" detailsClass="govuk-!-margin-bottom-5">
    <p>
      If the product was first placed on the market on or after 1 October 2021, or hasn't been placed on the market
      yet, you must use the new rescaled energy label.
    </p>
    <p>
      The product's energy efficiency class on the rescaled label will be lower than its class on the old label, and
      other values might also be calculated differently. Before you create a rescaled label, you need to make sure
      the values you're entering are based on the criteria for the rescaled label. You shouldn't copy values directly
      from the old label.
    </p>
    <p>
      Products placed on the market before 1 October 2021 can continue to use the old style label until 31 March 2023.
    </p>
  </@govukDetails.details>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.energyConsumption"/>
</@common.standardProductForm>
