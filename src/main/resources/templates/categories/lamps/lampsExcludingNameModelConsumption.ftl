<#include '../../layout.ftl'>

<@common.standardProductForm title="Lamps" includeSupplierNameModel=false>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukRadios.radio path="form.templateColour" radioItems=templateColour legendSize="h2"/>
</@common.standardProductForm>
