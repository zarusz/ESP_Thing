///<reference path="..\common.ng.ts"/>
///<reference path="..\..\app\app.module.ts"/>
///<reference path="principal.service.ts"/>
"use strict";
var App;
(function (App) {
    var Auth;
    (function (Auth) {
        var Authenticator = (function () {
            function Authenticator(rootScope, state, q, $translate, principal, AuthServerProvider, Account, Register, Activate, Password, PasswordResetInit, PasswordResetFinish, Tracker) {
                this.rootScope = rootScope;
                this.state = state;
                this.q = q;
                this.$translate = $translate;
                this.principal = principal;
                this.AuthServerProvider = AuthServerProvider;
                this.Account = Account;
                this.Register = Register;
                this.Activate = Activate;
                this.Password = Password;
                this.PasswordResetInit = PasswordResetInit;
                this.PasswordResetFinish = PasswordResetFinish;
                this.Tracker = Tracker;
            }
            Authenticator.prototype.login = function (credentials, callback) {
                var _this = this;
                var cb = callback || angular.noop;
                var deferred = this.q.defer();
                this.AuthServerProvider.login(credentials).then(function (data) {
                    // retrieve the logged account information
                    _this.principal.identity(true).then(function (account) {
                        // After the login the language will be changed to
                        // the language selected by the user during his registration
                        _this.$translate.use(account.langKey);
                        _this.$translate.refresh();
                        _this.Tracker.sendActivity();
                        deferred.resolve(data);
                    });
                    return cb();
                }).catch(function (err) {
                    _this.logout();
                    deferred.reject(err);
                    return cb(err);
                });
                return deferred.promise;
            };
            Authenticator.prototype.logout = function () {
                this.AuthServerProvider.logout();
                this.principal.authenticate(null);
            };
            Authenticator.prototype.authorize = function (force) {
                var _this = this;
                if (force === void 0) { force = false; }
                return this.principal.identity(force)
                    .then(function () {
                    var isAuthenticated = _this.principal.isAuthenticated();
                    if (_this.rootScope.toState.data && _this.rootScope.toState.data.roles && _this.rootScope.toState.data.roles.length > 0 && !_this.principal.isInAnyRole(_this.rootScope.toState.data.roles)) {
                        if (isAuthenticated) {
                            // user is signed in but not authorized for desired state
                            _this.state.go("accessdenied");
                        }
                        else {
                            // user is not authenticated. stow the state they wanted before you
                            // send them to the signin state, so you can return them when you're done
                            _this.rootScope.returnToState = _this.rootScope.toState;
                            _this.rootScope.returnToStateParams = _this.rootScope.toStateParams;
                            // now, send them to the signin state so they can log in
                            _this.state.go("login");
                        }
                    }
                });
            };
            Authenticator.prototype.createAccount = function (account, callback) {
                var _this = this;
                var cb = callback || angular.noop;
                return this.Register.save(account, function () {
                    return cb(account);
                }, function (err) {
                    _this.logout();
                    return cb(err);
                }).$promise;
            };
            Authenticator.prototype.updateAccount = function (account, callback) {
                var cb = callback || angular.noop;
                return this.Account.save(account, function () {
                    return cb(account);
                }, function (err) {
                    return cb(err);
                }).$promise;
            };
            Authenticator.prototype.activateAccount = function (key, callback) {
                var cb = callback || angular.noop;
                return this.Activate.get(key, function (response) {
                    return cb(response);
                }, function (err) {
                    return cb(err);
                }).$promise;
            };
            Authenticator.prototype.changePassword = function (newPassword, callback) {
                var cb = callback || angular.noop;
                return this.Password.save(newPassword, function () {
                    return cb();
                }, function (err) {
                    return cb(err);
                }).$promise;
            };
            Authenticator.prototype.resetPasswordInit = function (mail, callback) {
                var cb = callback || angular.noop;
                return this.PasswordResetInit.save(mail, function () {
                    return cb();
                }, function (err) {
                    return cb(err);
                }).$promise;
            };
            Authenticator.prototype.resetPasswordFinish = function (key, newPassword, callback) {
                var cb = callback || angular.noop;
                return this.PasswordResetFinish.save(key, newPassword, function () {
                    return cb();
                }, function (err) {
                    return cb(err);
                }).$promise;
            };
            Authenticator.$name = "Auth";
            Authenticator.$inject = [App.NgSvc.rootScope, App.NgSvc.state, App.NgSvc.q, App.NgSvc.translate, Auth.Principal.$name, "AuthServerProvider", "Account", "Register", "Activate", "Password", "PasswordResetInit", "PasswordResetFinish", "Tracker"];
            return Authenticator;
        }());
        Auth.Authenticator = Authenticator;
        App.$module.service(Authenticator.$name, Authenticator);
    })(Auth = App.Auth || (App.Auth = {}));
})(App || (App = {}));
//# sourceMappingURL=auth.service.js.map