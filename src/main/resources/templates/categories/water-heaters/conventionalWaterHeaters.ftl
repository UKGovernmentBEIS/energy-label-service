<#include '../../layout.ftl'>

<@common.standardProductForm title="Conventional water heaters">

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukRadios.radioGroup path="form.consumptionUnit">
      <@govukRadios.radioItem path="form.consumptionUnit" itemMap=energyUnitKw>
        <@govukTextInput.textInput path="form.kwhAnnum" />
      </@govukRadios.radioItem>
      <@govukRadios.radioItem path="form.consumptionUnit" itemMap=energyUnitGj>
        <@govukTextInput.textInput path="form.gjAnnum"/>
      </@govukRadios.radioItem>
      <@govukRadios.radioItem path="form.consumptionUnit" itemMap=energyUnitBoth>
        <@govukTextInput.textInput path="form.bothKwhAnnum"/>
        <@govukTextInput.textInput path="form.bothGjAnnum"/>
      </@govukRadios.radioItem>
    </@govukRadios.radioGroup>

    <@govukRadios.radioYesNo path="form.offPeak"/>

</@common.standardProductForm>