<#include '../../layout.ftl'>

<@common.standardProductForm "Electric ovens">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.volume"/>

  <@govukTextInput.textInput path="form.conventionalKwhConsumption"/>
  <@govukRadios.radioYesNo path="form.isFanOven" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.convectionKwhConsumption"/>
  </@govukRadios.radioYesNo>

</@common.standardProductForm>
