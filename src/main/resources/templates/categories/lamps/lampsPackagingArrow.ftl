<#include '../../layout.ftl'>

<@common.standardProductForm title="Energy rating arrow for light source packaging" includeSupplierNameModel=false showInsetText=false>
  <div class="govuk-inset-text">
    The government will implement a rescaled energy label for lighting products in Great Britain from September 2021.
    You can begin preparing for this now. This arrow must only be used on products which include a new-style rescaled energy label.
  </div>
  <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
  <@govukRadios.radio path="form.templateColour" radioItems=templateColour legendSize="h2"/>
  <@govukRadios.radio path="form.labelOrientation" radioItems=labelOrientationOptions legendSize="h2"/>
</@common.standardProductForm>
