<#include '../../layout.ftl'>

<#macro commonSpaceHeaterPackagesCalculatorFields showPreferentialHeaterInputs=true>
  <#if showPreferentialHeaterInputs>
    <@govukTextInput.textInput path="form.preferentialHeaterHeatOutput"/>
    <@govukTextInput.textInput path="form.preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage"/>
  </#if>

  <@govukFieldset.fieldset legendHeading="Solar Collector" legendSize="h2">
    <@govukTextInput.textInput path="form.solarCollectorSize"/>
    <@govukTextInput.textInput path="form.solarCollectorEfficiencyPercentage"/>
  </@govukFieldset.fieldset>

  <@govukRadios.radioYesNo path="form.hasTemperatureControl" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukSelect.select path="form.temperatureControlClass" options=temperatureControlClass/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.hasSupplementaryBoiler" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukTextInput.textInput path="form.supplementaryBoilerHeatOutput"/>
    <@govukTextInput.textInput path="form.supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage"/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.hasStorageTank" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukTextInput.textInput path="form.storageTankVolume"/>
    <@govukSelect.select path="form.storageTankRating" options=storageTankRating/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.spaceHeater"/>
</#macro>