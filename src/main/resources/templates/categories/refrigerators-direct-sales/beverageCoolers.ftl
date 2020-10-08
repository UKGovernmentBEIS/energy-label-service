<#include '../../layout.ftl'>

<@common.standardProductForm "Beverage coolers">
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukTextInput.textInput path="form.annualEnergyConsumption"/>
  <@govukTextInput.textInput path="form.capacity"/>
  <@govukTextInput.textInput path="form.compartmentTemp"/>
  <@govukTextInput.textInput path="form.maxAmbientTemp"/>
</@common.standardProductForm>
