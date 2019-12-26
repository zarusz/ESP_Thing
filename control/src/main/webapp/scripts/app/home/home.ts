/// <reference path="../../components/common.ng.ts" />
///<reference path="home.controller.ts"/>
"use strict";
module App.Home {

    export var $module = angular.module("Home", []);

    $module.config($stateProvider => {
        $stateProvider
            .state("home", {
                parent: "site",
                url: "/",
                data: {
                    roles: []
                },
                templateUrl: "scripts/app/home/home.html",
                controller: HomeCtrl,
                controllerAs: HomeCtrl.$as,
                resolve: {
                    mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                        $translatePartialLoader.addPart("main");
                        return $translate.refresh();
                    }]
                }
            });
    });

}
