<#include '../../layout.ftl'>

<@common.standardProductForm "Gas ovens">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.volume"/>

  <@govukTextInput.textInput path="form.conventionalKwhConsumption"/>
  <@govukTextInput.textInput path="form.conventionalMjConsumption"/>
  <@govukRadios.radioYesNo path="form.isFanOven" hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.convectionKwhConsumption"/>
    <@govukTextInput.textInput path="form.convectionMjConsumption"/>
  </@govukRadios.radioYesNo>

</@common.standardProductForm>
