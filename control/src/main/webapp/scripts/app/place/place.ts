///<reference path="..\app.module.ts"/>
///<reference path="place.controller.ts"/>
module App {

    $module
        .config(($stateProvider) => {
            $stateProvider
                .state("place", {
                    parent: "site",
                    url: "/place",
                    data: {
                        roles: []
                    },
                    templateUrl: "scripts/app/main/main.html",
                    controller: PlaceCtrl,
                    controllerAs: PlaceCtrl.$nameAs,
                    //views: {
                    //    "content@": {
                    //        templateUrl: "scripts/app/main/main.html",
                    //        controller: "MainController"
                    //    }
                    //},
                    resolve: {
                        mainTranslatePartialLoader: ["$translate", "$translatePartialLoader", function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                    }
                });
        });
}