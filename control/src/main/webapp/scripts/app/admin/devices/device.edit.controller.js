///<reference path="..\..\..\components\repository\repository.module.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var DeviceEditCtrl = (function () {
                function DeviceEditCtrl(deviceService, partitionService, state, stateParams) {
                    var _this = this;
                    this.deviceService = deviceService;
                    this.partitionService = partitionService;
                    this.state = state;
                    this.stateParams = stateParams;
                    this.partitions = [];
                    this.partitionService.loadRoot().then(function (x) { return _this.setPartitions(x); });
                    this.deviceService.getById(stateParams.deviceId).then(function (device) {
                        _this.setDevice(device);
                    });
                }
                DeviceEditCtrl.prototype.setDevice = function (d) {
                    this.device = d;
                };
                DeviceEditCtrl.prototype.setPartitions = function (p) {
                    var list = new Array();
                    this.flattenPartition(list, p);
                    this.partitions = list;
                };
                DeviceEditCtrl.prototype.flattenPartition = function (list, p) {
                    var _this = this;
                    list.push(p);
                    _.forEach(p.children, function (child) { return _this.flattenPartition(list, child); });
                };
                DeviceEditCtrl.$inject = [
                    App.Repository.DeviceService.$name,
                    App.Repository.PartitionService.$name,
                    App.NgSvc.state,
                    App.NgSvc.stateParams
                ];
                DeviceEditCtrl.$nameAs = "vm";
                return DeviceEditCtrl;
            }());
            Devices.DeviceEditCtrl = DeviceEditCtrl;
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=device.edit.controller.js.map