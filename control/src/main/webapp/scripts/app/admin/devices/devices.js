/// <reference path="../../../components/common.ng.ts" />
///<reference path="device.states.ts"/>
///<reference path="device.list.controller.ts"/>
///<reference path="device.edit.controller.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            Devices.$module = angular.module("Admin.Devices", []);
            Devices.$module
                .config(function ($stateProvider) {
                $stateProvider
                    .state(Devices.States.List, {
                    parent: "site",
                    url: "/admin/devices",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/device.list.html",
                    controller: Devices.DeviceListCtrl,
                    controllerAs: Devices.DeviceListCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart("main");
                                return $translate.refresh();
                            }]
                    }
                })
                    .state(Devices.States.Edit, {
                    parent: "site",
                    url: "/admin/device/{deviceId}",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/device.edit.html",
                    controller: Devices.DeviceEditCtrl,
                    controllerAs: Devices.DeviceEditCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart("main");
                                return $translate.refresh();
                            }]
                    }
                });
            });
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=devices.js.map