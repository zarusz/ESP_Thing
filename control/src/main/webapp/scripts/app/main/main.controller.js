///<reference path="..\app.module.ts"/>
'use strict';
var App;
(function (App) {
    var MainCtrl = (function () {
        function MainCtrl($scope, principal) {
            principal.identity().then(function (account) {
                $scope.account = account;
                $scope.isAuthenticated = principal.isAuthenticated;
            });
        }
        MainCtrl.$inject = ["$scope", "Principal"];
        return MainCtrl;
    })();
    App.MainCtrl = MainCtrl;
})(App || (App = {}));
//# sourceMappingURL=main.controller.js.map