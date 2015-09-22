///<reference path="..\app.module.ts"/>
///<reference path="partition.controller.ts"/>
var App;
(function (App) {
    App.$module.config(function ($stateProvider) {
        $stateProvider.state("partition", {
            parent: "site",
            url: "/partition/{partitionId}",
            //data: {
            //    partitionId: null
            //},
            params: {
                partitionId: null
            },
            templateUrl: "scripts/app/partition/partition.html",
            controller: App.PartitionCtrl,
            controllerAs: App.PartitionCtrl.$nameAs,
            resolve: {
                mainTranslatePartialLoader: ["$translate", "$translatePartialLoader", function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart("main");
                    return $translate.refresh();
                }]
            }
        });
    });
})(App || (App = {}));
//# sourceMappingURL=partition.js.map