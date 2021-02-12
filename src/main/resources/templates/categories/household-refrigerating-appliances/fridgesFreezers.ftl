<#include '../../layout.ftl'>

<@common.standardProductForm "Household fridges and freezers">
  <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2" isAboveDetailsComponent=true lastItemHasHiddenContent=(labelMode=='ENERGY')>
    <@common.postMarch2021RadioItem legislationCategories>

        <@govukTextInput.textInput path="form.qrCodeUrl"/>

        <@govukRadios.radioYesNo path="form.nonRatedCompartmentPostMarch2021" inline=false hiddenQuestionsWithYesSelected=true>
            <@govukTextInput.textInput path="form.nonRatedVolumePostMarch2021"/>
        </@govukRadios.radioYesNo>

        <@govukRadios.radioYesNo path="form.ratedCompartmentPostMarch2021" inline=false hiddenQuestionsWithYesSelected=true>
            <@govukTextInput.textInput path="form.ratedVolumePostMarch2021"/>
        </@govukRadios.radioYesNo>

        <@govukSelect.select path="form.noiseEmissionsClass" options=noiseEmissionsRating/>

    </@common.postMarch2021RadioItem>

    <@common.preMarch2021RadioItem legislationCategories>

      <@govukRadios.radioYesNo path="form.nonRatedCompartmentPreMarch2021" inline=false hiddenQuestionsWithYesSelected=true>
        <@govukTextInput.textInput path="form.nonRatedVolumePreMarch2021"/>
      </@govukRadios.radioYesNo>

      <@govukRadios.radioYesNo path="form.ratedCompartmentPreMarch2021" inline=false hiddenQuestionsWithYesSelected=true>
        <@govukTextInput.textInput path="form.ratedVolumePreMarch2021"/>
        <@govukSelect.select path="form.starRatingPreMarch2021" options=starRating/>
      </@govukRadios.radioYesNo>

    </@common.preMarch2021RadioItem>

  </@govukRadios.radioGroup>

  <@common.labelTypeGuidanceMarch2021/>

  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>

  <@govukTextInput.textInput path="form.noiseEmissions"/>
</@common.standardProductForm>
