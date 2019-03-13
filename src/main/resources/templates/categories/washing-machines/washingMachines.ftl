<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Washing machines" errorItems=errorList>
  <@form.govukForm submitUrl>

    <@govukTextInput.textInput path="form.supplierName"/>
    <@govukTextInput.textInput path="form.modelName"/>
    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
    <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
    <@govukTextInput.textInput path="form.annualWaterConsumption"/>
    <@govukTextInput.textInput path="form.capacity"/>

    <@govukSelect.select path="form.spinDryingEfficiencyRating" options=spinDryingEfficiencyRating/>

    <@govukTextInput.textInput path="form.washingNoiseEmissions"/>
    <@govukTextInput.textInput path="form.spinningNoiseEmissions"/>

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>