<#include '../../layout.ftl'>

<@common.standardProductForm title="Energy rating arrow for light source packaging" includeSupplierNameModel=false showInsetText=false>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukRadios.radio path="form.templateColour" radioItems=templateColour legendSize="h2"/>
  <@govukRadios.radio path="form.labelOrientation" radioItems=labelOrientationOptions legendSize="h2"/>
</@common.standardProductForm>
