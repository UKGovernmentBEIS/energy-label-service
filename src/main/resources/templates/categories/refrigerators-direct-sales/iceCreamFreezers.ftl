<#include '../../layout.ftl'>

<@common.standardProductForm title="Ice cream freezers" includePostMarch2021InternetLabellingFields=true>
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.capacity"/>
  <@govukTextInput.textInput path="form.compartmentTemp"/>
  <@govukTextInput.textInput path="form.maxAmbientTemp"/>
</@common.standardProductForm>
