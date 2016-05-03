///<reference path="..\app.module.ts"/>
///<reference path="main.controller.ts"/>
"use strict";
var App;
(function (App) {
    App.$module
        .config(function ($stateProvider) {
        $stateProvider
            .state("home", {
            parent: "site",
            url: "/",
            data: {
                roles: []
            },
            templateUrl: "scripts/app/main/main.html",
            controller: App.MainCtrl,
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
})(App || (App = {}));
//# sourceMappingURL=main.js.map