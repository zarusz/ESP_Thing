///<reference path="..\..\app\app.module.ts"/>
///<reference path="..\common.ts"/>
var App;
(function (App) {
    var Component;
    (function (Component) {
        var Partition;
        (function (_Partition) {
            var Device = (function () {
                function Device() {
                }
                return Device;
            })();
            _Partition.Device = Device;
            var Partition = (function () {
                function Partition() {
                }
                return Partition;
            })();
            _Partition.Partition = Partition;
            var PartitionService = (function () {
                function PartitionService($http) {
                    this.$http = $http;
                }
                PartitionService.prototype.loadRoot = function () {
                    return this.$http.get("/api/partition");
                };
                PartitionService.$name = "PartitionService";
                PartitionService.$inject = [App.NgSvc.http];
                return PartitionService;
            })();
            _Partition.PartitionService = PartitionService;
            App.$module.service(PartitionService.$name, PartitionService);
        })(Partition = Component.Partition || (Component.Partition = {}));
    })(Component = App.Component || (App.Component = {}));
})(App || (App = {}));
//# sourceMappingURL=partition.service.js.map