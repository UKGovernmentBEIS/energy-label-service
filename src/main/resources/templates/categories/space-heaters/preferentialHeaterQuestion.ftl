<#include '../../layout.ftl'>

<@defaultPage pageTitle="What is the preferential heater of the package?">
    <@form.govukForm submitUrl>

        <@govukRadios.radio path="form.category"
        radioItems=categories
        label="What is the preferential heater of the package?"
        legendHeadingClass="govuk-fieldset__legend--xl"
        />

        <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
    </@form.govukForm>

</@defaultPage>
