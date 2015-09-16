///<reference path="..\..\app.module.ts"/>
///<reference path="..\..\..\components\common.ts"/>
"use strict";
var App;
(function (App) {
    var LoginCtrl = (function () {
        function LoginCtrl($rootScope, $state, auth) {
            this.$rootScope = $rootScope;
            this.$state = $state;
            this.auth = auth;
            this.user = {};
            this.rememberMe = true;
            this.authenticationError = false;
        }
        LoginCtrl.prototype.login = function (form, event) {
            var _this = this;
            event.preventDefault();
            this.auth.login({
                username: form.username,
                password: form.password,
                rememberMe: form.rememberMe
            }).then(function () {
                _this.authenticationError = false;
                if (_this.$rootScope.previousStateName === "register") {
                    _this.$state.go("home");
                }
                else {
                    _this.$rootScope.back();
                }
            }).catch(function () {
                _this.authenticationError = true;
            });
        };
        LoginCtrl.$inject = [App.NgSvc.rootScope, App.NgSvc.state, "Auth"];
        return LoginCtrl;
    })();
    App.LoginCtrl = LoginCtrl;
})(App || (App = {}));
//# sourceMappingURL=login.controller.js.map