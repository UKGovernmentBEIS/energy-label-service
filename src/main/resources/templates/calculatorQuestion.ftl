<#include 'layout.ftl'>

<@defaultPage pageTitle="Do you need help calculating the energy rating for this package?">
    <@form.govukForm submitUrl>

        <@govukRadios.radio path="form.category"
        radioItems=categories
        label="Do you need help calculating the energy rating for this package?"
        legendHeadingClass="govuk-fieldset__legend--xl"
        />

        <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
    </@form.govukForm>

</@defaultPage>
