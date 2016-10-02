///<reference path="..\..\..\components\repository\repository.module.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var DeviceUpgradeCtrl = (function () {
                function DeviceUpgradeCtrl(deviceService, partitionService, state, stateParams, mdToast) {
                    var _this = this;
                    this.deviceService = deviceService;
                    this.partitionService = partitionService;
                    this.state = state;
                    this.stateParams = stateParams;
                    this.mdToast = mdToast;
                    this.deviceService.getById(stateParams.deviceId).then(function (device) {
                        _this.setDevice(device);
                    });
                }
                DeviceUpgradeCtrl.prototype.setDevice = function (d) {
                    this.device = {
                        firmwareUrl: null
                    };
                };
                DeviceUpgradeCtrl.prototype.upgrade = function (form) {
                    var _this = this;
                    if (form.$invalid) {
                        return;
                    }
                    this.deviceService.upgrade(this.stateParams.deviceId, this.device).then(function () {
                        _this.mdToast.show(_this.mdToast.simple().textContent("Upgrade submitted!"));
                    });
                };
                DeviceUpgradeCtrl.$inject = [
                    App.Repository.DeviceService.$name,
                    App.Repository.PartitionService.$name,
                    App.NgSvc.state,
                    App.NgSvc.stateParams,
                    App.NgSvc.mdToast
                ];
                DeviceUpgradeCtrl.$nameAs = "vm";
                return DeviceUpgradeCtrl;
            }());
            Devices.DeviceUpgradeCtrl = DeviceUpgradeCtrl;
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=device.upgrade.controller.js.map