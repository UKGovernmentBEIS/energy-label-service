<#include '../../layout.ftl'>

<#macro commonHeatPumpPackageAndCombinationFields>
  <@govukTextInput.textInput path="form.preferentialHeaterHeatOutput"/>
  <@govukTextInput.textInput path="form.preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage"/>
  <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyColderPercentage"/>
  <@govukTextInput.textInput path="form.preferentialHeatPumpSeasonalSpaceHeatingEfficiencyWarmerPercentage"/>
  <@govukRadios.radioYesNo path="form.lowTemperatureHeatPump"/>
</#macro>