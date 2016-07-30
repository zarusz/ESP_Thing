/// <reference path="../../components/common.ng.ts" />
///<reference path="partition.controller.ts"/>
var App;
(function (App) {
    var Partition;
    (function (Partition) {
        Partition.$module = angular.module("Partition", []);
        Partition.$module
            .config(function ($stateProvider) {
            $stateProvider
                .state("partition", {
                parent: "site",
                url: "/partition/{partitionId}",
                data: {
                    roles: []
                },
                params: {
                    partitionId: null
                },
                templateUrl: "scripts/app/partition/partition.html",
                controller: Partition.PartitionCtrl,
                controllerAs: Partition.PartitionCtrl.$nameAs,
                resolve: {
                    mainTranslatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                }
            });
        });
    })(Partition = App.Partition || (App.Partition = {}));
})(App || (App = {}));
//# sourceMappingURL=partition.js.map