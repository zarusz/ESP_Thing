///<reference path="../common.module.ts"/>
///<reference path="partition.service.ts"/>
///<reference path="device.service.ts"/>
var App;
(function (App) {
    var Repository;
    (function (Repository) {
        Repository.$module = angular.module("RepositoryModule", [App.Common.$module.name, "LocalStorageModule"]);
        Repository.$module.service(Repository.PartitionService.$name, Repository.PartitionService);
        Repository.$module.service(Repository.DeviceService.$name, Repository.DeviceService);
    })(Repository = App.Repository || (App.Repository = {}));
})(App || (App = {}));
//# sourceMappingURL=repository.module.js.map