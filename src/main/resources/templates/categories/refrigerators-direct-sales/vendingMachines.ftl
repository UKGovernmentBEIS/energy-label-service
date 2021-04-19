<#include '../../layout.ftl'>

<@common.standardProductForm
  title="Refrigerated vending machines"
  includeRescaledInternetLabellingFields=true
  beforeStandardInsetText="From summer 2021 an energy label will apply to refrigerated vending machines.
  These products will be required to display an energy label when placed on the market.">
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.fridgeCapacity"/>
  <@govukTextInput.textInput path="form.fridgeMaxTemp"/>
  <@govukRadios.radioYesNo path="form.frozenCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.freezerMaxTemp"/>
  </@govukRadios.radioYesNo>
</@common.standardProductForm>
