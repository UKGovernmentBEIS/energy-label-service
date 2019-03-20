<#include '../../layout.ftl'>

<@common.standardProductForm "Reversible ductless air conditioners">

  <@govukSelect.select path="form.coolingEfficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.coolingModeDesignLoad"/>
  <@govukTextInput.textInput path="form.coolingModeSeer"/>
  <@govukTextInput.textInput path="form.coolingAnnualEnergyConsumption"/>

  <@govukFieldset.fieldset legendHeading="Average climate conditions">
    <@govukSelect.select path="form.averageHeatingEfficiencyRating" options=efficiencyRating/>
    <@govukTextInput.textInput path="form.averageHeatingDesignLoad"/>
    <@govukTextInput.textInput path="form.averageScop"/>
    <@govukTextInput.textInput path="form.averageAnnualEnergyConsumption"/>
  </@govukFieldset.fieldset>

  <@govukFieldset.fieldset legendHeading="Warmer climate conditions">
    <@govukRadios.radioYesNo path="form.warmerConditions" inline=false hiddenQuestionsWithYesSelected=true>
      <@govukSelect.select path="form.warmerHeatingEfficiencyRating" options=efficiencyRating/>
      <@govukTextInput.textInput path="form.warmerHeatingDesignLoad"/>
      <@govukTextInput.textInput path="form.warmerScop"/>
      <@govukTextInput.textInput path="form.warmerAnnualEnergyConsumption"/>
    </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>

  <@govukFieldset.fieldset legendHeading="Colder climate conditions">
    <@govukRadios.radioYesNo path="form.colderConditions" inline=false hiddenQuestionsWithYesSelected=true>
      <@govukSelect.select path="form.colderHeatingEfficiencyRating" options=efficiencyRating/>
      <@govukTextInput.textInput path="form.colderHeatingDesignLoad"/>
      <@govukTextInput.textInput path="form.colderScop"/>
      <@govukTextInput.textInput path="form.colderAnnualEnergyConsumption"/>
    </@govukRadios.radioYesNo>
  </@govukFieldset.fieldset>

  <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>
  <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

</@common.standardProductForm>