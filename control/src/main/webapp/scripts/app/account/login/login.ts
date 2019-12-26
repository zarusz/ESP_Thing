///<reference path="..\..\app.module.ts"/>
///<reference path="login.controller.ts"/>
"use strict";

module App {
    $module
        .config(($stateProvider) => {
            $stateProvider
                .state("login", {
                    parent: "site",
                    url: "/login",
                    data: {
                        roles: [],
                        pageTitle: "login.title"
                    },
                    templateUrl: "scripts/app/account/login/login.html",
                    controller: LoginCtrl,
                    controllerAs: "lg",
                    resolve: {
                        translatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("login");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
