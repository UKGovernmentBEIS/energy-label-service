<#include '../../layout.ftl'>

<@common.standardProductForm "Bidirectional ventilation units">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.soundPowerLevel"/>
  <@govukTextInput.textInput path="form.maxFlowRate"/>
</@common.standardProductForm>
