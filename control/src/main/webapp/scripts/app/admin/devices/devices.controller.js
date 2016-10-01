///<reference path="..\..\..\components\repository\repository.module.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var DevicesCtrl = (function () {
                function DevicesCtrl(deviceService, state, stateParams) {
                    var _this = this;
                    this.deviceService = deviceService;
                    this.state = state;
                    this.stateParams = stateParams;
                    this.deviceService.getHubAll().then(function (d) { return d.data; }).then(function (devices) {
                        _this.setDevices(devices);
                    });
                }
                DevicesCtrl.prototype.setDevices = function (d) {
                    this.devices = d;
                };
                DevicesCtrl.$inject = [
                    App.Repository.DeviceService.$name,
                    App.NgSvc.state,
                    App.NgSvc.stateParams
                ];
                DevicesCtrl.$nameAs = "vm";
                return DevicesCtrl;
            }());
            Devices.DevicesCtrl = DevicesCtrl;
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=devices.controller.js.map