<#include '../../layout.ftl'>

<@common.standardProductForm "Televisions">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukRadios.radioYesNo path="form.powerSwitch"/>
  <@govukTextInput.textInput path="form.powerConsumption"/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.screenSizeCm"/>
  <@govukTextInput.textInput path="form.screenSizeInch"/>
</@common.standardProductForm>
