///<reference path="..\..\..\components\repository\repository.module.ts"/>
///<reference path="device.states.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var DeviceListCtrl = (function () {
                function DeviceListCtrl(deviceService, state, stateParams) {
                    var _this = this;
                    this.deviceService = deviceService;
                    this.state = state;
                    this.stateParams = stateParams;
                    this.deviceService.getHubAll().then(function (d) { return d.data; }).then(function (devices) {
                        _this.setDevices(devices);
                    });
                }
                DeviceListCtrl.prototype.setDevices = function (d) {
                    this.devices = d;
                };
                DeviceListCtrl.prototype.humanTime = function (d) {
                    return moment(d).fromNow();
                };
                DeviceListCtrl.prototype.editDevice = function (device) {
                    this.state.go(Devices.States.Edit, { deviceId: device.id });
                };
                DeviceListCtrl.$inject = [
                    App.Repository.DeviceService.$name,
                    App.NgSvc.state,
                    App.NgSvc.stateParams
                ];
                DeviceListCtrl.$nameAs = "vm";
                return DeviceListCtrl;
            }());
            Devices.DeviceListCtrl = DeviceListCtrl;
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=device.list.controller.js.map