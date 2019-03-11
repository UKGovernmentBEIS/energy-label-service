<#include '../../layout.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Washing machines">
  <@form.govukForm "/categories/washing-machines">

    <@govukTextInput.textInput path="form.supplierName" label="Supplier's name or trade mark"/>
    <@govukTextInput.textInput path="form.modelName" label="Supplier's model identifier"/>
    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class indicator" options=efficiencyRating/>
    <@govukTextInput.textInput path="form.annualEnergyConsumption" label="Weighted energy consumption (EC) in kWh per 1 000 hours, rounded up to the nearest integer"/>
    <@govukTextInput.textInput path="form.annualWaterConsumption" label="Weighted annual water consumption (AWC) in litres per year, rounded to the nearest integer [L/annum]"/>
    <@govukTextInput.textInput path="form.capacity" label="Rated capacity in kg, for the standard 60°C cotton programme at full load or the standard 40°C cotton programme at full load [kg]"/>

    <@govukSelect.select path="form.spinDryingEfficiencyRating" label="Spin-drying efficiency class" options=spinDryingEfficiencyRating/>

    <@govukTextInput.textInput path="form.washingNoiseEmissions" label="Airborne acoustical noise emissions during the washing phase for the standard 60°C cotton programme at full load expressed in dB(A) re 1pW"/>
    <@govukTextInput.textInput path="form.spinningNoiseEmissions" label="Airborne acoustical noise emissions during the spinning phase for the standard 60°C cotton programme at full load expressed in dB(A) re 1pW"/>

    <@govukButton.button buttonText="Generate Label" buttonClass="govuk-button--start govuk-!-margin-top-2 govuk-!-margin-bottom-8"/>
  </@form.govukForm>

</@defaultPage>