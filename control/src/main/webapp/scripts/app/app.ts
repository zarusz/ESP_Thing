///<reference path="..\components\common.ng.ts" />
///<reference path="..\components\common.eventbus.ts"/>
///<reference path="app.module.ts"/>
///<reference path="..\components\sidenav\sidenav.ctrl.ts"/>
///<reference path="place\place.controller.ts"/>
///<reference path="..\app\feature\feature.ts"/>
///<reference path="..\app\actionbar\actionbar.ctrl.ts"/>
module App.Main {

    var $module = angular.module("controlAppMain", [
        App.$module.name,
        Repository.$module.name,
        Partition.$module.name,
        Feature.$module.name
    ]);

    $module
        .factory("authInterceptor", ($rootScope, $q, $location, localStorageService) => {
            return {
                // Add authorization token to headers
                request: (config) => {
                    config.headers = config.headers || {};
                    var token = localStorageService.get("token");

                    if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                        config.headers.Authorization = "Bearer " + token.access_token;
                    }

                    return config;
                }
            };
        })
        .factory("authExpiredInterceptor", ($rootScope, $q, $injector, localStorageService) => {
            return {
                responseError: (response) => {
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
        $rootScope.$on("$stateChangeStart", (event, toState, toStateParams) => {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then((language) => {
                $translate.use(language);
            });

        });

        $rootScope.$on("$stateChangeSuccess", (event, toState, toParams, fromState, fromParams) => {
            var titleKey = "global.title";

            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;

            // Set the page title key to the one configured in state or use default one
            if (toState.data && toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }

            $translate(titleKey).then((title) => {
                // Change window title with translated one
                $window.document.title = title;
            });
        });

        $rootScope.back = function () {
            // If previous state is "activate" or do not exist go to "home"
            if ($rootScope.previousStateName === "activate" || $state.get($rootScope.previousStateName) === null) {
                $state.go("home");
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    }

    function Configure($stateProvider: ng.ui.IStateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider) {

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        var defaultState = {
            name: "site",
            abstract: true,
            data: {
              roles: ["ROLE_USER"]
            },
            templateUrl: "scripts/components/actionbar/actionbar.html",
            controller: Component.ActionBarCtrl,
            controllerAs: "actionBar",
            resolve: {
                authorize: ["Auth", (Auth) => {
                    return Auth.authorize();
                }
                ],
                translatePartialLoader: ["$translate", "$translatePartialLoader", ($translate, $translatePartialLoader) => {
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

    $module.run(Run);
    $module.config(Configure);

    $module.service(EventBus.$name, EventBus);
}
