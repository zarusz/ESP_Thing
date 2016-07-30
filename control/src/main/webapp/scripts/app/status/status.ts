/// <reference path="../../components/common.ng.ts" />
///<reference path="status.controller.ts"/>

module App.Status {

    export class States {
        static Status = "status";
    }

    export var $module = angular.module("Status", []);
    $module
        .config($stateProvider => {
            $stateProvider
                .state(States.Status, {
                    parent: "site",
                    url: "/status",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/status/status.html",
                    controller: StatusCtrl,
                    controllerAs: StatusCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
