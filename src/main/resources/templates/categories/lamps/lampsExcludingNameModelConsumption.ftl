<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Lamps">
  <@form.govukForm submitUrl>

    <@govukSelect.select path="form.efficiencyRating" label="Energy efficiency class of the application" options=efficiencyRating/>

    <@govukRadios.radio path="form.templateType" label="Which type of label should be generated? " radioItems=templateType />

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>