/// <reference path="../../../components/common.ng.ts" />
///<reference path="devices.controller.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var States = (function () {
                function States() {
                }
                States.Devices = "admin_devices";
                return States;
            }());
            Devices.States = States;
            Devices.$module = angular.module("Admin.Devices", []);
            Devices.$module
                .config(function ($stateProvider) {
                $stateProvider
                    .state(States.Devices, {
                    parent: "site",
                    url: "/admin/devices",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/admin/devices/devices.html",
                    controller: Devices.DevicesCtrl,
                    controllerAs: Devices.DevicesCtrl.$nameAs,
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