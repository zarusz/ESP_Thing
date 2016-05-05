/// <reference path="../../components/common.ng.ts" />
///<reference path="main.controller.ts"/>
"use strict";
module App.Main {

    export var $module = angular.module("Home", []);
    $module
        .config(($stateProvider) => {
            $stateProvider
                .state("home", {
                    parent: "site",
                    url: "/",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/main/main.html",
                    controller: MainCtrl,
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
