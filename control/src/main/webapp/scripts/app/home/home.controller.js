/// <reference path="../../components/common.ng.ts" />
/// <reference path="../../components/auth/auth.service.ts" />
"use strict";
var App;
(function (App) {
    var Home;
    (function (Home) {
        var HomeCtrl = (function () {
            function HomeCtrl(scope, principal) {
                principal.identity().then(function (account) {
                    scope.account = account;
                    scope.isAuthenticated = principal.isAuthenticated;
                });
            }
            HomeCtrl.$inject = [App.NgSvc.scope, App.Auth.Principal.$name];
            return HomeCtrl;
        }());
        Home.HomeCtrl = HomeCtrl;
    })(Home = App.Home || (App.Home = {}));
})(App || (App = {}));
//# sourceMappingURL=home.controller.js.map