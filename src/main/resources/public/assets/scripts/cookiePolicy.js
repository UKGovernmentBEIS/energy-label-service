(function() {
  // Always hide cookie banner on cookie prefs page
  window.ELS.elsCookieControl.hideCookieBanner();

  // Set radios to current preference (default to 'No')
  var prefsCookieVal = window.ELS.elsCookieControl.getPrefsCookieVal();

  if(prefsCookieVal === 'true') {
    document.getElementById('analytics-cookies-yes').checked = true;
  } else {
    document.getElementById('analytics-cookies-no').checked = true;
  }

  // Show the prefs radios
  document.getElementById('els-cookie-policy__settings--js-disabled').setAttribute('hidden', 'true');
  document.getElementById('els-cookie-policy__settings').removeAttribute('hidden');

  // Handle saving prefs
  document.getElementById('els-cookie-policy__settings-save-button').addEventListener('click', function() {
    if(document.getElementById('analytics-cookies-yes').checked) {
      window.ELS.elsCookieControl.acceptCookies();
    } else {
      window.ELS.elsCookieControl.rejectCookies();
    }
    document.getElementById('els-cookie-policy__notification-banner').removeAttribute('hidden');
    document.getElementById('els-cookie-policy__notification-banner').focus();
  })
})();
