<#include '../../layout.ftl'>

<@common.standardProductForm "Televisions">
  <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears legendSize="h2"/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukRadios.radioYesNo path="form.powerSwitch"/>
  <@govukTextInput.textInput path="form.powerConsumption"/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.screenSizeCm"/>
  <@govukTextInput.textInput path="form.screenSizeInch"/>
</@common.standardProductForm>
