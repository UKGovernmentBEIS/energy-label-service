<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Lamps">
  <@govukErrorSummary.errorSummary errorItems=errorList/>
  <@form.govukForm submitUrl>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukRadios.radio path="form.templateType" radioItems=templateType />

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>