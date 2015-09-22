///<reference path="..\..\components\repository\repository.module.ts"/>
var App;
(function (App) {
    var PartitionParams = (function () {
        function PartitionParams(partitionId) {
            this.partitionId = partitionId;
        }
        return PartitionParams;
    })();
    App.PartitionParams = PartitionParams;
    var PartitionCtrl = (function () {
        function PartitionCtrl(partitionService, deviceService, state, stateParams) {
            var _this = this;
            this.partitionService = partitionService;
            this.deviceService = deviceService;
            this.state = state;
            this.stateParams = stateParams;
            this.partitionService.getById(stateParams.partitionId).then(function (p) {
                _this.setPartition(p);
            });
        }
        PartitionCtrl.prototype.setPartition = function (p) {
            var _this = this;
            this.partition = p;
            this.enumCrumbs();
            this.devices = [];
            this.deviceService.getAllByPartitionId(p.id).success(function (devices) {
                _this.devices = devices;
            });
            //this.enumDevices(p);
        };
        PartitionCtrl.prototype.enumCrumbs = function () {
            var crumbs = [];
            for (var c = this.partition; c != null; c = c.parent) {
                crumbs.push(c);
            }
            this.crumbs = crumbs.reverse();
        };
        PartitionCtrl.$inject = [
            App.Repository.PartitionService.$name,
            App.Repository.DeviceService.$name,
            App.NgSvc.state,
            App.NgSvc.stateParams
        ];
        PartitionCtrl.$nameAs = "p";
        return PartitionCtrl;
    })();
    App.PartitionCtrl = PartitionCtrl;
})(App || (App = {}));
//# sourceMappingURL=partition.controller.js.map