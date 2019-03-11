<#include 'layout.ftl'>

<@defaultPage title="Energy Label Prototype" pageHeading="Label for Televisions Classes A to G">
  <p class="govuk-body">The Energy Label Generator aims to facilitate the implementation of the labelling delegated Regulations. With the web application, you can create a tailor-made energy label for one product in high resolution pdf format. This can then be used for printing purposes or on the Internet.</p>

  <@form.govukForm "/">

    <@govukSelect.select path="form.renderType" label="[debug] render type" options=renderTypeOptions/>

    <@govukTextInput.textInput path="form.manufacturer" label="Supplier's name or trade mark"/>
    <@govukTextInput.textInput path="form.modelName" label="Supplier's model identifier"/>
    <@govukSelect.select path="form.rating" label="Energy efficiency class indicator" options=ratingOptions/>
    <@govukTextInput.textInput path="form.powerConsumption" label="On-mode power consumption in Watts"/>
    <@govukTextInput.textInput path="form.annualPowerConsumption" label="Annual on-mode energy consumption [kWh/annum]"/>
    <@govukTextInput.textInput path="form.screenSizeCm" label="Visible screen size in diagonal in centimeters"/>
    <@govukTextInput.textInput path="form.screenSizeIn" label="Visible screen size in diagonal in inches"/>

    <@govukButton.button buttonText="Generate Label" buttonClass="govuk-button--start govuk-!-margin-top-2 govuk-!-margin-bottom-8"/>
  </@form.govukForm>

</@defaultPage>