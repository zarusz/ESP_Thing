/// <reference path="../../components/common.ng.ts" />
///<reference path="partition.controller.ts"/>
module App.Partition {

    export var $module = angular.module("Partition", []);
    $module
        .config(($stateProvider) => {
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
                    controller: PartitionCtrl,
                    controllerAs: PartitionCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: [NgSvc.translate, NgSvc.translatePartialLoader, ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
