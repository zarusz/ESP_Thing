/// <reference path="../../components/common.ng.ts" />
///<reference path="home.controller.ts"/>
"use strict";
var App;
(function (App) {
    var Home;
    (function (Home) {
        Home.$module = angular.module("Home", []);
        Home.$module.config(function ($stateProvider) {
            $stateProvider
                .state("home", {
                parent: "site",
                url: "/",
                data: {
                    roles: []
                },
                templateUrl: "scripts/app/home/home.html",
                controller: Home.HomeCtrl,
                controllerAs: Home.HomeCtrl.$as,
                resolve: {
                    mainTranslatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                }
            });
        });
    })(Home = App.Home || (App.Home = {}));
})(App || (App = {}));
//# sourceMappingURL=home.js.map