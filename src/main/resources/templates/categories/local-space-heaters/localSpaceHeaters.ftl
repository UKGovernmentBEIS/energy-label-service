<#include '../../layout.ftl'>

<@common.standardProductForm "Local space heaters">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.directHeatOutput"/>

  <@govukRadios.radioYesNo path="form.fluidTransfer" inline=false hiddenQuestionsWithYesSelected=true>
    <@govukTextInput.textInput path="form.indirectHeatOutput"/>
  </@govukRadios.radioYesNo>
</@common.standardProductForm>
