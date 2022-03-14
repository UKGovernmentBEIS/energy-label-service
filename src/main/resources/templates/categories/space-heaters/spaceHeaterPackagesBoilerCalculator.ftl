<#include '../../layout.ftl'>
<#import 'spaceHeaterPackagesCalculatorCommon.ftl' as commonFields>

<@common.standardProductForm
title="Packages of space heater, temperature control and solar device energy label calculator: ${preferentialHeater}"
showInsetText=false
>
  <@commonFields.commonSpaceHeaterPackagesCalculatorFields/>

  <@govukFieldset.fieldset legendHeading="Supplementary heat pump" legendSize="h2">
    <@govukRadios.radioYesNo path="form.hasSupplementaryHeatPump" hiddenQuestionsWithYesSelected=true legendSize="h3">
      <@govukTextInput.textInput path="form.supplementaryHeatPumpHeatOutput"/>
      <@govukTextInput.textInput path="form.supplementaryHeatPumpSeasonalSpaceHeatingEfficiencyPercentage"/>
    </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>
</@common.standardProductForm>