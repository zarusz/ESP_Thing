///<reference path="app.module.ts"/>
///<reference path="..\components\actionbar\actionbar.ctrl.ts"/>
///<reference path="..\components\sidenav\sidenav.ctrl.ts"/>
///<reference path="place\place.controller.ts"/>
///<reference path="..\components\common.eventbus.ts"/>
var App;
(function (App) {
    App.$module
        .factory("authInterceptor", function ($rootScope, $q, $location, localStorageService) {
        return {
            // Add authorization token to headers
            request: function (config) {
                config.headers = config.headers || {};
                var token = localStorageService.get("token");
                if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                    config.headers.Authorization = "Bearer " + token.access_token;
                }
                return config;
            }
        };
    })
        .factory("authExpiredInterceptor", function ($rootScope, $q, $injector, localStorageService) {
        return {
            responseError: function (response) {
                // token has expired
                if (response.status === 401 && (response.data.error == "invalid_token" || response.data.error == "Unauthorized")) {
                    localStorageService.remove("token");
                    var Principal = $injector.get("Principal");
                    if (Principal.isAuthenticated()) {
                        var Auth = $injector.get("Auth");
                        Auth.authorize(true);
                    }
                }
                return $q.reject(response);
            }
        };
    });
    function Run($rootScope, $location, $window, $http, $state, $translate, Language, Auth, Principal, ENV, VERSION) {
        $rootScope.ENV = ENV;
        $rootScope.VERSION = VERSION;
        $rootScope.$on("$stateChangeStart", function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }
            // Update the language
            Language.getCurrent().then(function (language) {
                $translate.use(language);
            });
        });
        $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {
            var titleKey = "global.title";
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
            // Set the page title key to the one configured in state or use default one
            if (toState.data && toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }
            $translate(titleKey).then(function (title) {
                // Change window title with translated one
                $window.document.title = title;
            });
        });
        $rootScope.back = function () {
            // If previous state is "activate" or do not exist go to "home"
            if ($rootScope.previousStateName === "activate" || $state.get($rootScope.previousStateName) === null) {
                $state.go("home");
            }
            else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    }
    function Configure($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider) {
        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);
        var defaultState = {
            name: "site",
            abstract: true,
            data: {
                roles: ["ROLE_USER"]
            },
            templateUrl: "scripts/components/actionbar/actionbar.html",
            controller: App.Component.ActionBarCtrl,
            controllerAs: "actionBar",
            resolve: {
                authorize: ["Auth", function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ["$translate", "$translatePartialLoader", function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart("global");
                    }]
            }
        };
        $urlRouterProvider.otherwise("/");
        $stateProvider
            .state(defaultState);
        $httpProvider.interceptors.push("authExpiredInterceptor");
        $httpProvider.interceptors.push("authInterceptor");
        // Initialize angular-translate
        $translateProvider.useLoader("$translatePartialLoader", {
            urlTemplate: "i18n/{lang}/{part}.json"
        });
        $translateProvider.preferredLanguage("en");
        $translateProvider.useCookieStorage();
        $translateProvider.useSanitizeValueStrategy("escaped");
        tmhDynamicLocaleProvider.localeLocationPattern("bower_components/angular-i18n/angular-locale_{{locale}}.js");
        tmhDynamicLocaleProvider.useCookieStorage();
        tmhDynamicLocaleProvider.storageKey("NG_TRANSLATE_LANG_KEY");
    }
    App.$module.run(Run);
    App.$module.config(Configure);
    App.$module.service(App.EventBus.$name, App.EventBus);
})(App || (App = {}));
//# sourceMappingURL=app.js.map