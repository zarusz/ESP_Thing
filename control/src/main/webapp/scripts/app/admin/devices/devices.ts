/// <reference path="../../../components/common.ng.ts" />
///<reference path="devices.controller.ts"/>

module App.Admin.Devices {

    export class States {
        static Devices = "admin_devices";
    }

    export var $module = angular.module("Admin.Devices", []);
    $module
        .config($stateProvider => {
            $stateProvider
                .state(States.Devices, {
                    parent: "site",
                    url: "/admin/devices",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/devices.html",
                    controller: DevicesCtrl,
                    controllerAs: DevicesCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
