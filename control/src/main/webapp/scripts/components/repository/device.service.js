///<reference path="..\common.ng.ts"/>
var App;
(function (App) {
    var Repository;
    (function (Repository) {
        var DeviceService = (function () {
            function DeviceService($http, q) {
                this.$http = $http;
                this.q = q;
            }
            DeviceService.prototype.getAllByPartitionId = function (partitionId) {
                return this.$http.get("/api/device", { params: { partitionId: partitionId } });
            };
            DeviceService.$name = "DeviceService";
            DeviceService.$inject = [App.NgSvc.http, App.NgSvc.$q];
            return DeviceService;
        })();
        Repository.DeviceService = DeviceService;
    })(Repository = App.Repository || (App.Repository = {}));
})(App || (App = {}));
//# sourceMappingURL=device.service.js.map