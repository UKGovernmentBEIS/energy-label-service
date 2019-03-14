<#include '../../layout.ftl'>

<@common.standardProductForm "Lamps">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.energyConsumption"/>
</@common.standardProductForm>