<#include '../../layout.ftl'>

<#macro commonSpaceHeaterPackagesCalculatorFields>
  <@govukTextInput.textInput path="form.preferentialHeaterHeatOutput"/>
  <@govukTextInput.textInput path="form.preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage"/>

  <@govukFieldset.fieldset legendHeading="Temperature control" legendSize="h2">
    <@govukRadios.radioYesNo path="form.hasTemperatureControl" hiddenQuestionsWithYesSelected=true legendSize="h3">
      <@govukSelect.select path="form.temperatureControlClass" options=temperatureControlClass/>
    </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>

  <@govukFieldset.fieldset legendHeading="Supplementary boiler" legendSize="h2">
    <@govukRadios.radioYesNo path="form.hasSupplementaryBoiler" hiddenQuestionsWithYesSelected=true legendSize="h3">
      <@govukTextInput.textInput path="form.supplementaryBoilerHeatOutput"/>
      <@govukTextInput.textInput path="form.supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage"/>
    </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>

  <@govukFieldset.fieldset legendHeading="Solar collector" legendSize="h2">
    <@govukRadios.radioYesNo path="form.hasSolarCollector" hiddenQuestionsWithYesSelected=true legendSize="h3">
      <@govukTextInput.textInput path="form.solarCollectorSize"/>
      <@govukTextInput.textInput path="form.solarCollectorEfficiencyPercentage"/>
   </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>

  <@govukFieldset.fieldset legendHeading="Storage tank" legendSize="h2">
    <@govukRadios.radioYesNo path="form.hasStorageTank" hiddenQuestionsWithYesSelected=true legendSize="h3">
      <@govukTextInput.textInput path="form.storageTankVolume"/>
      <@govukSelect.select path="form.storageTankRating" options=storageTankRating/>
    </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>

  <@govukFieldset.fieldset legendHeading="Space heater" legendSize="h2">
    <@govukRadios.radioYesNo path="form.spaceHeater"/>
  </@govukFieldset.fieldset>
</#macro>