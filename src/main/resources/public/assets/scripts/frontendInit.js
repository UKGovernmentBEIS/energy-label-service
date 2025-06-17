const govukJsUrl = document.getElementById('els-init-script').getAttribute('data-govuk-js-url');
const { initAll } = await import(govukJsUrl);

initAll();