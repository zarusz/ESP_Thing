/// <reference path="../../../components/common.ng.ts" />
///<reference path="device.states.ts"/>
///<reference path="device.list.controller.ts"/>
///<reference path="device.edit.controller.ts"/>
///<reference path="device.upgrade.controller.ts"/>

module App.Admin.Devices {

    export var $module = angular.module("Admin.Devices", []);
    $module
        .config($stateProvider => {
            $stateProvider
                .state(States.List, {
                    parent: "site",
                    url: "/admin/devices",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/device.list.html",
                    controller: DeviceListCtrl,
                    controllerAs: "vm",
                    resolve: {
                        mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                })
                .state(States.Edit, {
                    parent: "site",
                    url: "/admin/device/{deviceId}",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/device.edit.html",
                    controller: DeviceEditCtrl,
                    controllerAs: "vm",
                    resolve: {
                        mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                })
                .state(States.Upgrade, {
                    parent: "site",
                    url: "/admin/device/{deviceId}/upgrade",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/device.upgrade.html",
                    controller: DeviceUpgradeCtrl,
                    controllerAs: "vm",
                    resolve: {
                        mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
