<#include '../../layout.ftl'>

<#macro commonBoilerPackageAndCombinationFields>
  <@govukRadios.radioYesNo path="form.supplementaryHeatPump" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukTextInput.textInput path="form.supplementaryHeatPumpHeatOutput"/>
    <@govukTextInput.textInput path="form.supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage"/>
  </@govukRadios.radioYesNo>
</#macro>