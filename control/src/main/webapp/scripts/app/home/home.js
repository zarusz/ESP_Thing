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
                    roles: ["ROLE_USER"]
                },
                templateUrl: "scripts/app/home/home.html",
                controller: Home.HomeCtrl,
                //views: {
                //    "content@": {
                //        templateUrl: "scripts/app/main/main.html",
                //        controller: "MainController"
                //    }
                //},
                resolve: {
                    mainTranslatePartialLoader: ["$translate", "$translatePartialLoader", function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                }
            });
        });
    })(Home = App.Home || (App.Home = {}));
})(App || (App = {}));
//# sourceMappingURL=home.js.map