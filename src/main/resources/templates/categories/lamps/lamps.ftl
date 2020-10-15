<#include '../../layout.ftl'>

<@common.standardProductForm "Lamps and light sources">
    <@govukRadios.radioGroup path="form.applicableLegislation" legendSize="h2">
        <@common.preSeptember2021RadioItem legislationCategories/>

        <@common.postSeptember2021RadioItem legislationCategories>
            <@govukTextInput.textInput path="form.qrCodeUrl"/>
            <@govukRadios.radio path="form.templateSize" radioItems=templateSize legendSize="h2"/>
            <@govukRadios.radio path="form.templateColour" radioItems=templateColour legendSize="h2"/>
        </@common.postSeptember2021RadioItem>
    </@govukRadios.radioGroup>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.energyConsumption"/>
</@common.standardProductForm>
