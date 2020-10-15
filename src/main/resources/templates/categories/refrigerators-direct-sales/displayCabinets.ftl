<#include '../../layout.ftl'>

<@common.standardProductForm title="Supermarket refrigerator/freezer cabinets or gelato-scooping cabinets" includeRescaledInternetLabellingFields=true>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukRadios.radioYesNo path="form.chilledCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.fridgeCapacity"/>
    <@govukTextInput.textInput path="form.fridgeMaxTemp"/>
    <@govukTextInput.textInput path="form.fridgeMinTemp"/>
  </@govukRadios.radioYesNo>
  <@govukRadios.radioYesNo path="form.frozenCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.freezerCapacity"/>
    <@govukTextInput.textInput path="form.freezerMaxTemp"/>
    <@govukTextInput.textInput path="form.freezerMinTemp"/>
  </@govukRadios.radioYesNo>
</@common.standardProductForm>
