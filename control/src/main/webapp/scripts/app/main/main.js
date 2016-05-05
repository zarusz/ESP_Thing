/// <reference path="../../components/common.ng.ts" />
///<reference path="main.controller.ts"/>
"use strict";
var App;
(function (App) {
    var Main;
    (function (Main) {
        Main.$module = angular.module("Home", []);
        Main.$module
            .config(function ($stateProvider) {
            $stateProvider
                .state("home", {
                parent: "site",
                url: "/",
                data: {
                    roles: []
                },
                templateUrl: "scripts/app/main/main.html",
                controller: Main.MainCtrl,
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
    })(Main = App.Main || (App.Main = {}));
})(App || (App = {}));
//# sourceMappingURL=main.js.map