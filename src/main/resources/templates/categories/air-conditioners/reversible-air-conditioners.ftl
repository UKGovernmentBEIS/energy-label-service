<#include '../../layout.ftl'>

  <#--TODO reverse router and FieldPrompt etc.-->
<@defaultPage pageHeading="Reversible air conditioners">
  <@form.govukForm "/air-conditioners/non-duct/reversible-air-conditioners">

    <@govukTextInput.textInput path="form.supplierName" label="Supplier's name or trade mark"/>
    <@govukTextInput.textInput path="form.modelIdentifier" label="Supplier's model identifier"/>
    <@govukSelect.select path="form.overallEfficiencyClass" label="The seasonal space heating energy efficiency class under average climate conditions for medium- temperature" options=ratingOptions/>
    <@govukTextInput.textInput path="form.coolingModeDesignLoad" label="Cooling mode: design load in kW"/>
    <@govukTextInput.textInput path="form.coolingModeSeer" label="Cooling mode: seasonal energy efficiency ratio (SEER value)"/>
    <@govukTextInput.textInput path="form.annualEnergyConsumption" label="Annual energy consumption in kWh per year, for cooling/heating [kWh/annum]"/>

    <@govukFieldset.fieldset legendHeading="Average climate conditions" legendSize="h2">
      <@govukSelect.select path="form.averageEfficiencyClass" label="Energy efficiency class of the application" options=ratingOptions/>
      <@govukTextInput.textInput path="form.averageHeatingModeDesignLoad" label="Heating mode: design load in kW"/>
      <@govukTextInput.textInput path="form.averageAnnualEnergyConsumption" label="Annual energy consumption in kWh per year, for heating [kWh/annum]"/>
      <@govukTextInput.textInput path="form.averageScop" label="Heating mode: seasonal energy efficiency ratio (SCOP value)"/>
    </@govukFieldset.fieldset>

    <@govukFieldset.fieldset legendHeading="Warmer climate conditions" legendSize="h2">
      <@govukRadios.radioYesNo path="form.wamerScopAvailable" label="Do you have Seasonal Coefficient of Performance (SCOP) values for warmer climate conditions?" inline=false hiddenQuestionsWithYesSelected=true>
        <@govukSelect.select path="form.warmerEfficiencyClass" label="Energy efficiency class of the application" options=ratingOptions/>
        <@govukTextInput.textInput path="form.warmerHeatingModeDesignLoad" label="Heating mode: design load in kW"/>
        <@govukTextInput.textInput path="form.warmerAnnualEnergyConsumption" label="Annual energy consumption in kWh per year, for heating [kWh/annum]"/>
        <@govukTextInput.textInput path="form.warmerScop" label="Heating mode: seasonal energy efficiency ratio (SCOP value)"/>
      </@govukRadios.radioYesNo>
    </@govukFieldset.fieldset>

    <@govukFieldset.fieldset legendHeading="Colder climate conditions" legendSize="h2">
      <@govukRadios.radioYesNo path="form.colderScopAvailable" label="Do you have Seasonal Coefficient of Performance (SCOP) values for colder climate conditions?" inline=false hiddenQuestionsWithYesSelected=true>
        <@govukSelect.select path="form.colderEfficiencyClass" label="Energy efficiency class of the application" options=ratingOptions/>
        <@govukTextInput.textInput path="form.colderHeatingModeDesignLoad" label="Heating mode: design load in kW"/>
        <@govukTextInput.textInput path="form.colderAnnualEnergyConsumption" label="Annual energy consumption in kWh per year, for heating [kWh/annum]"/>
        <@govukTextInput.textInput path="form.colderScop" label="Heating mode: seasonal energy efficiency ratio (SCOP value)"/>
      </@govukRadios.radioYesNo>
    </@govukFieldset.fieldset>


    <@govukButton.button buttonText="Generate Label" buttonClass="govuk-button--start govuk-!-margin-top-2 govuk-!-margin-bottom-8"/>
  </@form.govukForm>

</@defaultPage>