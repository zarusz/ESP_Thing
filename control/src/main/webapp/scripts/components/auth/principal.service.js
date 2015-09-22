///<reference path="..\common.ng.ts"/>
///<reference path="..\..\app\app.module.ts"/>
'use strict';
var App;
(function (App) {
    var Auth;
    (function (Auth) {
        var Identity = (function () {
            function Identity() {
            }
            return Identity;
        })();
        Auth.Identity = Identity;
        var Principal = (function () {
            function Principal($q, account, tracker) {
                this.$q = $q;
                this.account = account;
                this.tracker = tracker;
                this._authenticated = false;
            }
            Principal.prototype.isIdentityResolved = function () {
                return angular.isDefined(this._identity);
            };
            Principal.prototype.isAuthenticated = function () {
                return this._authenticated;
            };
            Principal.prototype.isInRole = function (role) {
                if (!this._authenticated || !this._identity || !this._identity.roles) {
                    return false;
                }
                return this._identity.roles.indexOf(role) !== -1;
            };
            Principal.prototype.isInAnyRole = function (roles) {
                if (!this._authenticated || !this._identity.roles) {
                    return false;
                }
                for (var i = 0; i < roles.length; i++) {
                    if (this.isInRole(roles[i])) {
                        return true;
                    }
                }
                return false;
            };
            Principal.prototype.authenticate = function (identity) {
                this._identity = identity;
                this._authenticated = identity !== null;
            };
            Principal.prototype.identity = function (force) {
                var _this = this;
                var deferred = this.$q.defer();
                if (force === true) {
                    this._identity = undefined;
                }
                // check and see if we have retrieved the identity data from the server.
                // if we have, reuse it by immediately resolving
                if (angular.isDefined(this._identity)) {
                    deferred.resolve(this._identity);
                    return deferred.promise;
                }
                // retrieve the identity data from the server, update the identity object, and then resolve.
                this.account.get().$promise.then(function (account1) {
                    _this._identity = account1.data;
                    _this._authenticated = true;
                    deferred.resolve(_this._identity);
                    _this.tracker.connect();
                }).catch(function () {
                    _this._identity = null;
                    _this._authenticated = false;
                    deferred.resolve(_this._identity);
                });
                return deferred.promise;
            };
            Principal.$name = "Principal";
            Principal.$inject = [App.NgSvc.$q, "Account", "Tracker"];
            return Principal;
        })();
        Auth.Principal = Principal;
        App.$module.service(Principal.$name, Principal);
    })(Auth = App.Auth || (App.Auth = {}));
})(App || (App = {}));
//# sourceMappingURL=principal.service.js.map