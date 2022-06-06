<#include '../../layout.ftl'>

<@common.standardProductForm "Televisions and electronic displays">
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.efficiencyRatingSdr" options=efficiencyRatingSdr/>
  <@govukTextInput.textInput path="form.energyConsumptionSdr1000h"/>
  <@govukRadios.radioYesNo path="form.isHdr" hiddenQuestionsWithYesSelected=true>
    <@govukSelect.select path="form.efficiencyRatingHdr" options=efficiencyRatingHdr/>
    <@govukTextInput.textInput path="form.energyConsumptionHdr1000h"/>
  </@govukRadios.radioYesNo>
  <@govukTextInput.textInput path="form.horizontalPixels"/>
  <@govukTextInput.textInput path="form.verticalPixels"/>

  <@govukTextInput.textInput path="form.screenSizeCm"/>
  <@govukTextInput.textInput path="form.screenSizeInch"/>

  <@common.rescaledInternetLabellingFields/>
</@common.standardProductForm>
