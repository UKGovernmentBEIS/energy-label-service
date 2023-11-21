(function() {
  var measurementId = 'G-MXY8G0TL9D';
  var elsCookieControl = {
    prefsCookieName: 'els-cookies-allowed',
    nonEssentialCookies: [
      { name: '_ga', path: '/'},
      { name: '_ga_'+measurementId.replace('G-',''), path: '/'},
    ],
    cookieScripts: [
      // Google Analytics
      function() {
        if(window.ELS.googleAnalyticsEnabled) {
          var gaScript = document.createElement("script");
          gaScript.src = "https://www.googletagmanager.com/gtag/js?id=" + measurementId;
          document.head.appendChild(gaScript);

          window.dataLayer = window.dataLayer || [];
          function gtag(){dataLayer.push(arguments);}
          gtag('js', new Date());

          gtag('config', measurementId);

          if(document.getElementById('googleAnalyticsClientId')) {
            gtag('get', measurementId, 'client_id', function (clientId)  {
              document.getElementById('googleAnalyticsClientId').value = clientId;
            })
          }
        } else {
          console.warn("You've consented to analytics cookies but Google Analytics integration is disabled on this environment, so the Google Analytics script won't be loaded.");
        }
      }
    ],
    denyConsentScripts: [
      // Google Analytics
      function() {
        // dataLayer will only exist on window if they've already consented to analytics. If that's the case, we update
        // consent preferences in the Google tag to ensure cookies aren't recreated
        if(window.hasOwnProperty("dataLayer")) {
          function gtag() {
            dataLayer.push(arguments);
          }

          gtag("consent", "default", {
            ad_storage: "denied",
            analytics_storage: "denied",
            functionality_storage: "denied",
            personalization_storage: "denied",
            security_storage: "denied"
          });
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
    eraseCookie: function (name, path) {
      document.cookie = name+'=;path='+path+';Max-Age=-99999999;';
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
      elsCookieControl.runDenyConsentScripts();
    },
    deleteNonEssentialCookies: function () {
      for(var i = 0; i < elsCookieControl.nonEssentialCookies.length; i++) {
        var cookie = elsCookieControl.nonEssentialCookies[i];
        elsCookieControl.eraseCookie(cookie.name, cookie.path);
      }
    },
    runDenyConsentScripts: function () {
      for(var i = 0; i < elsCookieControl.denyConsentScripts.length; i++) {
        elsCookieControl.denyConsentScripts[i]();
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
