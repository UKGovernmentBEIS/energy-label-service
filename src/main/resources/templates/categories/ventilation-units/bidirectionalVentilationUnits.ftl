<#include '../../layout.ftl'>
<#include '../../generateLabelButton.ftl'>

<@defaultPage pageHeading="Bidirectional ventilation units">
  <@govukErrorSummary.errorSummary errorItems=errorList/>
  <@form.govukForm submitUrl>

    <@govukTextInput.textInput path="form.supplierName"/>
    <@govukTextInput.textInput path="form.modelName"/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>

    <@govukTextInput.textInput path="form.soundPowerLevel"/>

    <@govukTextInput.textInput path="form.maxFlowRate"/>

    <@generateLabelButton/>
  </@form.govukForm>

</@defaultPage>