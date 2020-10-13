<#include '../../layout.ftl'>

<@common.standardProductForm "Televisions and electronic displays">

  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2" showNestedForInternetLabels=true>
    <@common.preMarch2021RadioItem legislationCategories>
      <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
      <@govukRadios.radioYesNo path="form.powerSwitch"/>
      <@govukTextInput.textInput path="form.powerConsumption"/>
      <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
    </@common.preMarch2021RadioItem>

    <@common.postMarch2021RadioItem legislationCategories>
      <@govukTextInput.textInput path="form.qrCodeUrl"/>
      <@govukSelect.select path="form.efficiencyRatingSdr" options=efficiencyRatingSdr/>
      <@govukTextInput.textInput path="form.energyConsumptionSdr1000h"/>
      <@govukRadios.radioYesNo path="form.isHdr" inline=false hiddenQuestionsWithYesSelected=true>
        <@govukSelect.select path="form.efficiencyRatingHdr" options=efficiencyRatingHdr/>
        <@govukTextInput.textInput path="form.energyConsumptionHdr1000h"/>
      </@govukRadios.radioYesNo>
      <@govukTextInput.textInput path="form.horizontalPixels"/>
      <@govukTextInput.textInput path="form.verticalPixels"/>
    </@common.postMarch2021RadioItem>
  </@govukRadios.radioGroup>

  <@govukTextInput.textInput path="form.screenSizeCm"/>
  <@govukTextInput.textInput path="form.screenSizeInch"/>
</@common.standardProductForm>
