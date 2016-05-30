///<reference path="..\..\components\repository\repository.module.ts"/>
var App;
(function (App) {
    var Status;
    (function (Status) {
        var StatusCtrl = (function () {
            function StatusCtrl(deviceService, state, stateParams) {
                var _this = this;
                this.deviceService = deviceService;
                this.state = state;
                this.stateParams = stateParams;
                this.deviceService.getHubAll().then(function (d) {
                    _this.setDevices(d.data);
                });
            }
            StatusCtrl.prototype.setDevices = function (d) {
                this.devices = d;
            };
            StatusCtrl.$inject = [
                App.Repository.DeviceService.$name,
                App.NgSvc.state,
                App.NgSvc.stateParams
            ];
            StatusCtrl.$nameAs = "s";
            return StatusCtrl;
        }());
        Status.StatusCtrl = StatusCtrl;
    })(Status = App.Status || (App.Status = {}));
})(App || (App = {}));
//# sourceMappingURL=status.controller.js.map