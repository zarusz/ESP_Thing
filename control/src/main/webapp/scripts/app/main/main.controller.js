'use strict';
var App;
(function (App) {
    var Main;
    (function (Main) {
        var MainCtrl = (function () {
            function MainCtrl($scope, principal) {
                principal.identity().then(function (account) {
                    $scope.account = account;
                    $scope.isAuthenticated = principal.isAuthenticated;
                });
            }
            MainCtrl.$inject = ["$scope", "Principal"];
            return MainCtrl;
        }());
        Main.MainCtrl = MainCtrl;
    })(Main = App.Main || (App.Main = {}));
})(App || (App = {}));
//# sourceMappingURL=main.controller.js.map