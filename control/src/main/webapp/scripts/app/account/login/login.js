///<reference path="..\..\app.module.ts"/>
///<reference path="login.controller.ts"/>
"use strict";
var App;
(function (App) {
    App.$module
        .config(function ($stateProvider) {
        $stateProvider
            .state("login", {
            parent: "site",
            url: "/login",
            data: {
                roles: [],
                pageTitle: "login.title"
            },
            templateUrl: "scripts/app/account/login/login.html",
            controller: App.LoginCtrl,
            controllerAs: "lg",
            resolve: {
                translatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart("login");
                        return $translate.refresh();
                    }]
            }
        });
    });
})(App || (App = {}));
//# sourceMappingURL=login.js.map