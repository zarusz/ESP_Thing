'use strict';
var App;
(function (App) {
    var Home;
    (function (Home) {
        var HomeCtrl = (function () {
            function HomeCtrl($scope, principal) {
                principal.identity().then(function (account) {
                    $scope.account = account;
                    $scope.isAuthenticated = principal.isAuthenticated;
                });
            }
            HomeCtrl.$inject = ["$scope", "Principal"];
            return HomeCtrl;
        }());
        Home.HomeCtrl = HomeCtrl;
    })(Home = App.Home || (App.Home = {}));
})(App || (App = {}));
//# sourceMappingURL=home.controller.js.map