///<reference path="..\common.ng.ts"/>
///<reference path="..\..\app\app.module.ts"/>
"use strict";

module App.Auth {

    export class Authenticator {
        static $name = "Auth";
        static $inject = [NgSvc.rootScope, NgSvc.state, NgSvc.q, NgSvc.translate, Principal.$name, "AuthServerProvider", "Account", "Register", "Activate", "Password", "PasswordResetInit", "PasswordResetFinish", "Tracker"];

        constructor(private rootScope:ng.IRootScopeService,
                    private state:ng.ui.IStateService,
                    private q:ng.IQService,
                    private $translate:any,
                    private principal:Principal,
                    private AuthServerProvider:any,
                    private Account:any,
                    private Register:any,
                    private Activate:any,
                    private Password:any,
                    private PasswordResetInit:any,
                    private PasswordResetFinish:any,
                    private Tracker:any) {

        }

        login(credentials, callback) {
            var cb = callback || angular.noop;
            var deferred = this.q.defer();

            this.AuthServerProvider.login(credentials).then((data) => {
                // retrieve the logged account information
                this.principal.identity(true).then((account) => {

                    // After the login the language will be changed to
                    // the language selected by the user during his registration
                    this.$translate.use(account.langKey);
                    this.$translate.refresh();
                    this.Tracker.sendActivity();
                    deferred.resolve(data);
                });
                return cb();
            }).catch((err) => {
                this.logout();
                deferred.reject(err);
                return cb(err);
            });

            return deferred.promise;
        }

        logout() {
            this.AuthServerProvider.logout();
            this.principal.authenticate(null);
        }

        authorize(force) {
            return this.principal.identity(force)
                .then(() => {
                    var isAuthenticated = this.principal.isAuthenticated();

                    if (this.rootScope.toState.data && this.rootScope.toState.data.roles && this.rootScope.toState.data.roles.length > 0 && !this.principal.isInAnyRole(this.rootScope.toState.data.roles)) {
                        if (isAuthenticated) {
                            // user is signed in but not authorized for desired state
                            this.state.go("accessdenied");
                        }
                        else {
                            // user is not authenticated. stow the state they wanted before you
                            // send them to the signin state, so you can return them when you're done
                            this.rootScope.returnToState = this.rootScope.toState;
                            this.rootScope.returnToStateParams = this.rootScope.toStateParams;

                            // now, send them to the signin state so they can log in
                            this.state.go("login");
                        }
                    }
                });
        }

        createAccount(account, callback) {
            var cb = callback || angular.noop;

            return this.Register.save(account, () => {
                    return cb(account);
                }, (err) => {
                    this.logout();
                    return cb(err);
                }).$promise;
        }

        updateAccount(account, callback) {
            var cb = callback || angular.noop;

            return this.Account.save(account,
                () => {
                    return cb(account);
                },
                (err) => {
                    return cb(err);
                }).$promise;
        }

        activateAccount(key, callback) {
            var cb = callback || angular.noop;

            return this.Activate.get(key, (response) => {
                    return cb(response);
                }, (err) => {
                    return cb(err);
                }).$promise;
        }

        changePassword(newPassword, callback) {
            var cb = callback || angular.noop;

            return this.Password.save(newPassword, () => {
                return cb();
            }, (err) => {
                return cb(err);
            }).$promise;
        }

        resetPasswordInit(mail, callback) {
            var cb = callback || angular.noop;

            return this.PasswordResetInit.save(mail, () => {
                return cb();
            }, (err) => {
                return cb(err);
            }).$promise;
        }

        resetPasswordFinish(key, newPassword, callback) {
            var cb = callback || angular.noop;

            return this.PasswordResetFinish.save(key, newPassword, () => {
                return cb();
            }, (err) => {
                return cb(err);
            }).$promise;
        }
    }

    $module.service(Authenticator.$name, Authenticator);

}
