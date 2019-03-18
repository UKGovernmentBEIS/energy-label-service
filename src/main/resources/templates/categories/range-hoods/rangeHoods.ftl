<#include '../../layout.ftl'>

<@common.standardProductForm "Range hoods">
  <@govukRadios.radio path="form.applicableLegislation" radioItems=legislationYears/>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukSelect.select path="form.fluidClass" options=secondaryRating/>
  <@govukSelect.select path="form.lightingClass" options=secondaryRating/>
  <@govukSelect.select path="form.greaseClass" options=secondaryRating/>
  <@govukTextInput.textInput path="form.noiseValue"/>
</@common.standardProductForm>
