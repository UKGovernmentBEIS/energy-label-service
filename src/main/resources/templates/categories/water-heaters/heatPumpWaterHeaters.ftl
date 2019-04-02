<#include '../../layout.ftl'>

<@common.standardProductForm title="Heat pump water heaters">

  <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

  <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

  <@govukTextInput.textInput path="form.soundPowerLevelOutdoors"/>

  <@govukRadios.radioGroup path="form.consumptionUnit">
    <@govukRadios.radioItem path="form.consumptionUnit" itemMap=energyUnitKw>
      <@govukFieldset.fieldset legendHeading="Annual electricity consumption in kWh/annum" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.colderKwhAnnumSingle"/>
        <@govukTextInput.textInput path="form.averageKwhAnnumSingle"/>
        <@govukTextInput.textInput path="form.warmerKwhAnnumSingle"/>
      </@govukFieldset.fieldset>
    </@govukRadios.radioItem>
    <@govukRadios.radioItem path="form.consumptionUnit" itemMap=energyUnitGj>
      <@govukFieldset.fieldset legendHeading="Annual fuel consumption in GJ/annum" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.colderGjAnnumSingle"/>
        <@govukTextInput.textInput path="form.averageGjAnnumSingle"/>
        <@govukTextInput.textInput path="form.warmerGjAnnumSingle"/>
      </@govukFieldset.fieldset>
    </@govukRadios.radioItem>
    <@govukRadios.radioItem path="form.consumptionUnit" itemMap=energyUnitBoth>
      <@govukFieldset.fieldset legendHeading="Annual electricity consumption in kWh/annum" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.colderKwhAnnumBoth"/>
        <@govukTextInput.textInput path="form.averageKwhAnnumBoth"/>
        <@govukTextInput.textInput path="form.warmerKwhAnnumBoth"/>
      </@govukFieldset.fieldset>
      <@govukFieldset.fieldset legendHeading="Annual fuel consumption in GJ/annum" legendHeadingClass="govuk-fieldset__legend--s" legendSize="h3">
        <@govukTextInput.textInput path="form.colderGjAnnumBoth"/>
        <@govukTextInput.textInput path="form.averageGjAnnumBoth"/>
        <@govukTextInput.textInput path="form.warmerGjAnnumBoth"/>
      </@govukFieldset.fieldset>
    </@govukRadios.radioItem>
  </@govukRadios.radioGroup>

  <@govukRadios.radioYesNo path="form.canRunOffPeakOnly"/>

</@common.standardProductForm>