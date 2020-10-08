<#include '../../layout.ftl'>

<@common.standardProductForm "Refrigerated vending machines">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.fridgeCapacity"/>
  <@govukTextInput.textInput path="form.fridgeMaxTemp"/>
  <@govukRadios.radioYesNo path="form.frozenCompartment" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.freezerMaxTemp"/>
  </@govukRadios.radioYesNo>
</@common.standardProductForm>