<#include '../../layout.ftl'>

<#macro commonSpaceHeaterPackagesCalculatorFields showPreferentialHeaterInputs=true showSolarCollector=true>
  <#if showPreferentialHeaterInputs>
    <@govukTextInput.textInput path="form.preferentialHeaterHeatOutput"/>
    <@govukTextInput.textInput path="form.preferentialHeaterSeasonalSpaceHeatingEfficiencyPercentage"/>
  </#if>

  <#if showSolarCollector>
    <@govukFieldset.fieldset legendHeading="Solar collector" legendSize="h2">
      <@govukTextInput.textInput path="form.solarCollectorSize"/>
      <@govukTextInput.textInput path="form.solarCollectorEfficiencyPercentage"/>
    </@govukFieldset.fieldset>
  </#if>

  <@govukRadios.radioYesNo path="form.temperatureControl" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukSelect.select path="form.temperatureControlClass" options=temperatureControlClass/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.supplementaryBoiler" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukTextInput.textInput path="form.supplementaryBoilerHeatOutput"/>
    <@govukTextInput.textInput path="form.supplementaryBoilerSeasonalSpaceHeatingEfficiencyPercentage"/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.storageTank" hiddenQuestionsWithYesSelected=true legendSize="h3">
    <@govukTextInput.textInput path="form.storageTankVolume"/>
    <@govukSelect.select path="form.storageTankRating" options=storageTankRating/>
  </@govukRadios.radioYesNo>

  <@govukRadios.radioYesNo path="form.spaceHeater"/>
</#macro>