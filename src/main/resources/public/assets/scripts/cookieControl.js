(function() {
  var elsCookieControl = {
    prefsCookieName: 'els-cookies-allowed',
    nonEssentialCookieNames: [
      '_gat_govuk_shared',
      '_ga',
      '_gat',
      '_gid'
    ],
    cookieScripts: [
      // Google Analytics
      function() {
        if(window.ELS.googleAnalyticsEnabled) {
          (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function () {
              (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
            a = s.createElement(o),
              m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m)
          })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

          ga('create', 'UA-145652997-1', 'auto', 'govuk_shared', {'allowLinker': true});
          ga('govuk_shared.require', 'linker');
          ga('govuk_shared.linker.set', 'anonymizeIp', true);
          ga('govuk_shared.linker:autoLink', ['www.gov.uk']);

          ga('create', 'UA-136887405-1', 'auto');
          ga('send', 'pageview');
          ga('govuk_shared.send', 'pageview');

          if(document.getElementById('googleAnalyticsClientId')) {
            ga(function (tracker) {
              var clientId = tracker.get('clientId');
              document.getElementById('googleAnalyticsClientId').value = clientId;
            });
          }
        } else {
          console.warn("You've consented to analytics cookies but Google Analytics integration is disabled on this environment, so the Google Analytics script won't be loaded.");
        }
      }
    ],
    setCookie: function (name,value,days) {
      var expires = "";
      if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
      }
      document.cookie = name + "=" + (value || "")  + expires + "; path=/";
    },
    getCookie: function (name) {
      var nameEQ = name + "=";
      var ca = document.cookie.split(';');
      for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
      }
      return null;
    },
    eraseCookie: function (name) {
      document.cookie = name+'=; Max-Age=-99999999;';
    },
    getBanner: function() {
      return document.getElementById('els-cookie-banner');
    },
    showCookieBanner: function() {
      document.getElementById('els-cookie-banner__accept').addEventListener('click', function() {
        elsCookieControl.acceptCookies();
        document.getElementById('els-cookie-banner__options').setAttribute('hidden','true');
        document.getElementById('els-cookie-banner__accepted-message').removeAttribute('hidden');
        document.getElementById('els-cookie-banner__accepted-message').focus();
      })

      document.getElementById('els-cookie-banner__reject').addEventListener('click', function() {
        elsCookieControl.rejectCookies();
        document.getElementById('els-cookie-banner__options').setAttribute('hidden','true');
        document.getElementById('els-cookie-banner__rejected-message').removeAttribute('hidden');
        document.getElementById('els-cookie-banner__rejected-message').focus();
      })

      document.getElementById('els-cookie-banner__accepted-hide').addEventListener('click', function() {
        elsCookieControl.hideCookieBanner();
      })

      document.getElementById('els-cookie-banner__rejected-hide').addEventListener('click', function() {
        elsCookieControl.hideCookieBanner();
      })

      elsCookieControl.getBanner().removeAttribute('hidden');
    },
    hideCookieBanner: function() {
      elsCookieControl.getBanner().setAttribute('hidden', 'true');
    },
    getPrefsCookieVal: function() {
      return elsCookieControl.getCookie(elsCookieControl.prefsCookieName);
    },
    checkPrefsCookie: function() {
      var prefsCookieVal = elsCookieControl.getPrefsCookieVal();
      if(prefsCookieVal === null) {
        elsCookieControl.showCookieBanner();
      } else if (prefsCookieVal === 'true') {
        elsCookieControl.cookiesAllowed();
      }
    },
    acceptCookies: function() {
      elsCookieControl.setCookie(elsCookieControl.prefsCookieName, 'true', 365);
      elsCookieControl.cookiesAllowed();
    },
    rejectCookies: function() {
      elsCookieControl.setCookie(elsCookieControl.prefsCookieName, 'false', 365);
      elsCookieControl.deleteNonEssentialCookies();
    },
    deleteNonEssentialCookies: function () {
      for(var i = 0; i < elsCookieControl.nonEssentialCookieNames.length; i++) {
        elsCookieControl.eraseCookie(elsCookieControl.nonEssentialCookieNames[i]);
      }
    },
    cookiesAllowed: function() {
      for(var i = 0; i < elsCookieControl.cookieScripts.length; i++) {
        elsCookieControl.cookieScripts[i]();
      }
    },
    init: function() {
      elsCookieControl.checkPrefsCookie();
    }
  };

  window.ELS.elsCookieControl = elsCookieControl;
  window.ELS.elsCookieControl.init();

})();
