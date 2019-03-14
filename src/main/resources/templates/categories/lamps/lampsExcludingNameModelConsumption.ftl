<#include '../../layout.ftl'>

<@common.standardProductForm title="Lamps" includeSupplierNameModel=false>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukRadios.radio path="form.templateType" radioItems=templateType />
</@common.standardProductForm>