/// <reference path="../../components/common.ng.ts" />
///<reference path="status.controller.ts"/>

module App.Status {

    export var $module = angular.module("Status", []);
    $module
        .config(($stateProvider) => {
            $stateProvider
                .state("status", {
                    parent: "site",
                    url: "/status",
                    data: {
                        roles: ["ROLE_USER"]
                    },
                    params: {
                        partitionId: null
                    },
                    templateUrl: "scripts/app/status/status.html",
                    controller: StatusCtrl,
                    controllerAs: StatusCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: ["$translate", "$translatePartialLoader", ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
