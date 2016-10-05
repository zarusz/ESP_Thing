///<reference path="..\..\..\components\repository\repository.module.ts"/>
var App;
(function (App) {
    var Admin;
    (function (Admin) {
        var Devices;
        (function (Devices) {
            var DeviceEditCtrl = (function () {
                function DeviceEditCtrl(deviceService, partitionService, state, stateParams, mdToast) {
                    var _this = this;
                    this.deviceService = deviceService;
                    this.partitionService = partitionService;
                    this.state = state;
                    this.stateParams = stateParams;
                    this.mdToast = mdToast;
                    this.partitions = [];
                    this.displayIcons = [
                        "fa-cube",
                        "fa-cubes",
                        "fa-compass",
                        "fa-envelope",
                        "fa-folder",
                        "fa-flask",
                        "fa-flag",
                        "fa-flash",
                        "fa-fire"
                    ];
                    this.partitionService.loadRoot().then(function (x) { return _this.setPartitions(x); });
                    this.deviceService.getById(stateParams.deviceId).then(function (device) {
                        _this.setDevice(device);
                    });
                }
                DeviceEditCtrl.prototype.setDevice = function (d) {
                    this.device = {
                        displayName: d.displayName,
                        displayIcon: d.displayIcon,
                        partition: d.partition ? { id: d.partition.id } : null
                    };
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
                DeviceEditCtrl.prototype.save = function (form) {
                    var _this = this;
                    if (form.$invalid) {
                        return;
                    }
                    this.deviceService.update(this.stateParams.deviceId, this.device).then(function () {
                        _this.mdToast.show(_this.mdToast.simple().textContent("Saved").hideDelay(3000));
                    });
                };
                DeviceEditCtrl.$inject = [
                    App.Repository.DeviceService.$name,
                    App.Repository.PartitionService.$name,
                    App.NgSvc.state,
                    App.NgSvc.stateParams,
                    App.NgSvc.mdToast
                ];
                return DeviceEditCtrl;
            }());
            Devices.DeviceEditCtrl = DeviceEditCtrl;
        })(Devices = Admin.Devices || (Admin.Devices = {}));
    })(Admin = App.Admin || (App.Admin = {}));
})(App || (App = {}));
//# sourceMappingURL=device.edit.controller.js.map