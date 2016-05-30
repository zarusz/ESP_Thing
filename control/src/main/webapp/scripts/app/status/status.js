/// <reference path="../../components/common.ng.ts" />
///<reference path="status.controller.ts"/>
var App;
(function (App) {
    var Status;
    (function (Status) {
        Status.$module = angular.module("Status", []);
        Status.$module
            .config(function ($stateProvider) {
            $stateProvider
                .state("status", {
                parent: "site",
                url: "/status",
                data: {
                    roles: ["ROLE_USER"]
                },
                params: {
                    partitionId: null
                },
                templateUrl: "scripts/app/status/status.html",
                controller: Status.StatusCtrl,
                controllerAs: Status.StatusCtrl.$nameAs,
                resolve: {
                    mainTranslatePartialLoader: ["$translate", "$translatePartialLoader", function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                }
            });
        });
    })(Status = App.Status || (App.Status = {}));
})(App || (App = {}));
//# sourceMappingURL=status.js.map