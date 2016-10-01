///<reference path="..\common.ng.ts"/>
///<reference path="model.ts"/>
var App;
(function (App) {
    var Repository;
    (function (Repository) {
        var PartitionModel = (function () {
            function PartitionModel() {
            }
            return PartitionModel;
        }());
        Repository.PartitionModel = PartitionModel;
        var PartitionService = (function () {
            function PartitionService(http, q) {
                this.http = http;
                this.q = q;
                this.partitionById = {};
                this.loadRoot();
            }
            PartitionService.prototype.loadRoot = function () {
                var _this = this;
                this.rootLoading = this.http.get("/api/partition").then(function (p) { return p.data; });
                this.rootLoading.then(function (p) {
                    _this.setRoot(p);
                    _this.rootLoading = null;
                });
                return this.rootLoading;
            };
            PartitionService.prototype.setRoot = function (newRoot) {
                this.root = newRoot;
                this.partitionById = {};
                this.processNode(newRoot, null);
            };
            PartitionService.prototype.processNode = function (node, parent) {
                var _this = this;
                node.parent = parent;
                this.partitionById[node.id] = node;
                _.forEach(node.children, function (child) { return _this.processNode(child, node); });
            };
            PartitionService.prototype.getById = function (partitionId) {
                var _this = this;
                var deferred = this.q.defer();
                if (this.rootLoading) {
                    this.rootLoading.then(function (p) {
                        var partition = _this.partitionById[partitionId];
                        deferred.resolve(partition);
                    });
                }
                else {
                    var partition = this.partitionById[partitionId];
                    deferred.resolve(partition);
                }
                return deferred.promise;
            };
            PartitionService.$name = "PartitionService";
            PartitionService.$inject = [App.NgSvc.http, App.NgSvc.q];
            return PartitionService;
        }());
        Repository.PartitionService = PartitionService;
    })(Repository = App.Repository || (App.Repository = {}));
})(App || (App = {}));
//# sourceMappingURL=partition.service.js.map