///<reference path="..\app.module.ts"/>
///<reference path="partition.controller.ts"/>
module App {

    $module
        .config(($stateProvider) => {
            $stateProvider
                .state("partition", {
                    parent: "site",
                    url: "/partition/{partitionId}",
                    data: {
                        roles: ["ROLE_USER"]
                    },
                    params: {
                        partitionId: null
                    },
                    templateUrl: "scripts/app/partition/partition.html",
                    controller: PartitionCtrl,
                    controllerAs: PartitionCtrl.$nameAs,
                    resolve: {
                        mainTranslatePartialLoader: ["$translate", "$translatePartialLoader", ($translate, $translatePartialLoader) => {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}
