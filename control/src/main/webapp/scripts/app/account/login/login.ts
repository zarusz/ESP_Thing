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
                    //views: {
                    //    "content@": {
                    //        templateUrl: "scripts/app/account/login/login.html",
                    //        controller: "LoginController"
                    //    }
                    //},
                    resolve: {
                        translatePartialLoader: ["$translate", "$translatePartialLoader", function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("login");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}