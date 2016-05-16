/// <reference path="../../components/common.ng.ts" />
///<reference path="home.controller.ts"/>
"use strict";
module App.Home {

    export var $module = angular.module("Home", []);
    $module.config(($stateProvider) => {
        $stateProvider
            .state("home", {
                parent: "site",
                url: "/",
                data: {
                    roles: ["ROLE_USER"]
                },
                templateUrl: "scripts/app/home/home.html",
                controller: HomeCtrl,
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

}
