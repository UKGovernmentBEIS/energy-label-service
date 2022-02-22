<#include '../../layout.ftl'>

<@common.standardProductForm "Range hoods">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukSelect.select path="form.fluidClass" options=secondaryRating/>
  <@govukRadios.radioYesNo path="form.lightingSystem" hiddenQuestionsWithYesSelected=true>
    <@govukSelect.select path="form.lightingClass" options=secondaryRating/>
  </@govukRadios.radioYesNo>
  <@govukSelect.select path="form.greaseClass" options=secondaryRating/>
  <@govukTextInput.textInput path="form.noiseValue"/>
</@common.standardProductForm>
