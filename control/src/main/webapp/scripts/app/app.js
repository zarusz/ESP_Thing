///<reference path="..\components\common.ng.ts" />
///<reference path="..\components\common.eventbus.ts"/>
///<reference path="..\components\auth\auth.service.ts"/>
///<reference path="app.module.ts"/>
///<reference path="..\components\sidenav\sidenav.ctrl.ts"/>
///<reference path="place\place.controller.ts"/>
///<reference path="..\app\feature\feature.ts"/>
///<reference path="..\app\actionbar\actionbar.ctrl.ts"/>
///<reference path="..\app\home\home.ts"/>
///<reference path="..\app\admin\devices\devices.ts"/>
var App;
(function (App) {
    var Main;
    (function (Main) {
        var $module = angular.module("controlAppMain", [
            App.$module.name,
            App.Repository.$module.name,
            App.Partition.$module.name,
            App.Feature.$module.name,
            App.Home.$module.name,
            App.Admin.Devices.$module.name
        ]);
        $module
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
        /*
        .factory("errorInterceptor", ($q, $mdToast) => {
            return {
                responseError: (response) => {
                    $mdToast.show($mdToast.simple().textContent("The operation failed.").hideDelay(3000));
                    return $q.reject(response);
                }
            };
        });
        */
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
                    roles: []
                },
                templateUrl: "scripts/app/actionbar/actionbar.html",
                controller: App.ActionBarCtrl,
                controllerAs: "vm",
                resolve: {
                    authorize: [App.Auth.Authenticator.$name, function (auth) {
                            return auth.authorize(false);
                        }],
                    translatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function (translate, translatePartialLoader) {
                            translatePartialLoader.addPart("global");
                        }]
                }
            };
            $urlRouterProvider.otherwise("/");
            $stateProvider
                .state(defaultState);
            $httpProvider.interceptors.push("authExpiredInterceptor");
            $httpProvider.interceptors.push("authInterceptor");
            //$httpProvider.interceptors.push("errorInterceptor");
            //enable CSRF
            $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
            $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
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
    })(Main = App.Main || (App.Main = {}));
})(App || (App = {}));
//# sourceMappingURL=app.js.map