/// <reference path="../../components/common.ng.ts" />
///<reference path="status.controller.ts"/>
var App;
(function (App) {
    var Status;
    (function (Status) {
        var States = (function () {
            function States() {
            }
            States.Status = "status";
            return States;
        }());
        Status.States = States;
        Status.$module = angular.module("Status", []);
        Status.$module
            .config(function ($stateProvider) {
            $stateProvider
                .state(States.Status, {
                parent: "site",
                url: "/status",
                data: {
                    roles: []
                },
                templateUrl: "scripts/app/status/status.html",
                controller: Status.StatusCtrl,
                controllerAs: Status.StatusCtrl.$nameAs,
                resolve: {
                    mainTranslatePartialLoader: [App.NgSvc.translate, App.NgSvc.translatePartialLoader, function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart("main");
                            return $translate.refresh();
                        }]
                }
            });
        });
    })(Status = App.Status || (App.Status = {}));
})(App || (App = {}));
//# sourceMappingURL=status.js.map