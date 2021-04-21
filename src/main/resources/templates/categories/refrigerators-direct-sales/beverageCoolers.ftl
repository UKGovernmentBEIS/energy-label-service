<#include '../../layout.ftl'>

<@common.standardProductForm
  title="Beverage coolers"
  includeRescaledInternetLabellingFields=true
  beforeStandardInsetText="From summer 2021 an energy label will apply to beverage coolers.
  These products will be required to display an energy label when placed on the market."
>
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.capacity"/>
  <@govukTextInput.textInput path="form.compartmentTemp"/>
  <@govukTextInput.textInput path="form.maxAmbientTemp"/>
</@common.standardProductForm>
