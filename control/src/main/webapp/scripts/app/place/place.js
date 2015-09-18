///<reference path="..\app.module.ts"/>
///<reference path="place.controller.ts"/>
var App;
(function (App) {
    App.$module.config(function ($stateProvider) {
        $stateProvider.state("place", {
            parent: "site",
            url: "/place",
            data: {
                roles: []
            },
            templateUrl: "scripts/app/main/main.html",
            controller: App.PlaceCtrl,
            controllerAs: App.PlaceCtrl.$nameAs,
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
})(App || (App = {}));
//# sourceMappingURL=place.js.map