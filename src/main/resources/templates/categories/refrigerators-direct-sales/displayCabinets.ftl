<#include '../../layout.ftl'>

<@common.standardProductForm
  title="Supermarket refrigerator/freezer cabinets or gelato-scooping cabinets"
  includeRescaledInternetLabellingFields=true
  beforeStandardInsetText="From summer 2021 an energy label will apply to supermarket refrigerator and freezer cabinets,
  and gelato-scooping cabinets.
  These products will be required to display an energy label when placed on the market."
>
  <@govukTextInput.textInput path="form.qrCodeUrl"/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukRadios.radioYesNo path="form.chilledCompartment" hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.fridgeCapacity"/>
    <@govukTextInput.textInput path="form.fridgeMaxTemp"/>
    <@govukTextInput.textInput path="form.fridgeMinTemp"/>
  </@govukRadios.radioYesNo>
  <@govukRadios.radioYesNo path="form.frozenCompartment" hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.freezerCapacity"/>
    <@govukTextInput.textInput path="form.freezerMaxTemp"/>
    <@govukTextInput.textInput path="form.freezerMinTemp"/>
  </@govukRadios.radioYesNo>
</@common.standardProductForm>
